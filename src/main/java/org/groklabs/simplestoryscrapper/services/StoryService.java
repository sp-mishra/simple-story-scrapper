package org.groklabs.simplestoryscrapper.services;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.groklabs.simplestoryscrapper.dtos.ChapterRecord;
import org.groklabs.simplestoryscrapper.dtos.StoryRecord;
import org.groklabs.simplestoryscrapper.entites.Author;
import org.groklabs.simplestoryscrapper.entites.Chapter;
import org.groklabs.simplestoryscrapper.entites.Chunk;
import org.groklabs.simplestoryscrapper.entites.Story;
import org.groklabs.simplestoryscrapper.repos.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
@Slf4j
@AllArgsConstructor
public class StoryService {
    private static final int CHUNK_SIZE = 5000;
    private final StoryRepository storyRepository;

    public String addStory(@Nonnull StoryRecord storyRecord) {
        final Author author = Author.builder()
                .firstName(storyRecord.author())
                .lastName(storyRecord.author())
                .penName(storyRecord.author())
                .build();
        final List<Chapter> chapters = extractChapters(storyRecord);

        final Story story = Story.builder()
                .title(storyRecord.title())
                .genre(storyRecord.genres())
                .author(author)
                .chapters(chapters)
                .build();

        final Story savedStory = storyRepository.save(story); // Assuming storyRepository is already injected

        log.info("Story with title: {} added successfully", storyRecord.title());
        return savedStory.getId().toString();
    }

    private List<Chunk> textToChunks(@Nonnull String text) {
        int index = 0;
        long chunkSequence = 1L;
        final List<Chunk> chunks = new ArrayList<>((text.length() / CHUNK_SIZE) + 1);
        while (index < text.length()) {
            // Determine the end index for the substring; ensure it does not exceed text length
            int endIndex = min(index + CHUNK_SIZE, text.length());
            final String content = text.substring(index, endIndex);
            // Create a new Chunk for each substring
            final Chunk chunk = Chunk.builder()
                    .sequence(chunkSequence++)
                    .content(content)
                    .build();
            chunks.add(chunk);
            index += CHUNK_SIZE; // Move to the next chunk
        }
        return chunks;
    }

    private List<Chapter> extractChapters(@Nonnull StoryRecord storyRecord) {
        final List<Chapter> chapters = new ArrayList<>();
        storyRecord.chapters().forEach(chapterRecord -> {
            final List<Chunk> chunks = textToChunks(chapterRecord.text());
            final Chapter chapter = Chapter.builder()
                    .sequence(chapterRecord.sequence())
                    .title(chapterRecord.title())
                    .chunks(chunks) // Assuming there's a method in Chapter.Builder to set chunks
                    .build();
            chapters.add(chapter);
        });
        return chapters;
    }

    private List<Chapter> extractChapters(@Nonnull StoryRecord storyRecord, List<Chapter> existingChapters) {
        final Map<Long, Chapter> existingChaptersMap = existingChapters.stream()
                .collect(Collectors.toMap(Chapter::getSequence, chapter -> chapter));
        final List<Chapter> updatedChapters = new ArrayList<>();
        storyRecord.chapters().forEach(chapterRecord -> {
            Chapter existingChapter = existingChaptersMap.get(chapterRecord.sequence());
            if (existingChapter != null) {
                // Update existing chapter details
                existingChapter.setTitle(chapterRecord.title());
                existingChapter.setChunks(textToChunks(chapterRecord.text()));
                updatedChapters.add(existingChapter);
            } else {
                // Create and add new chapter
                final List<Chunk> chunks = textToChunks(chapterRecord.text());
                final Chapter newChapter = Chapter.builder()
                        .sequence(chapterRecord.sequence())
                        .title(chapterRecord.title())
                        .chunks(chunks)
                        .build();
                updatedChapters.add(newChapter);
            }
        });
        return updatedChapters;
    }

    public String updateStory(@Nonnull Long storyId, @Nonnull StoryRecord storyRecord) {
        return storyRepository.findById(storyId).map(existingStory -> {
            existingStory.setTitle(storyRecord.title());
            existingStory.setGenre(storyRecord.genres());
            // Get existing chapters and pass them to extractChapters for update or addition
            List<Chapter> updatedChapters = extractChapters(storyRecord, existingStory.getChapters());
            existingStory.setChapters(updatedChapters);

            Story updatedStory = storyRepository.save(existingStory);
            log.info("Story with id: {} updated successfully", storyId);
            return updatedStory.getId().toString();
        }).orElseThrow(() -> new RuntimeException("Story with id: " + storyId + " not found"));
    }

    public Long updateOrInsertChapter(@Nonnull Long storyId, @Nonnull ChapterRecord chapterRecord) {
        return storyRepository.findById(storyId).map(existingStory -> {
            boolean chapterExists = false;
            for (Chapter chapter : existingStory.getChapters()) {
                if (chapter.getTitle().equals(chapterRecord.title())) {
                    chapter.setSequence(chapterRecord.sequence());
                    chapter.setChunks(textToChunks(chapterRecord.text()));
                    chapterExists = true;
                    break;
                }
            }
            if (!chapterExists) {
                final List<Chunk> chunks = textToChunks(chapterRecord.text());
                final Chapter newChapter = Chapter.builder()
                        .sequence(chapterRecord.sequence())
                        .title(chapterRecord.title())
                        .chunks(chunks)
                        .story(existingStory)
                        .build();
                existingStory.getChapters().add(newChapter);
            }
            Story updatedStory = storyRepository.save(existingStory);
            log.info("Chapter '{}' updated or inserted successfully in story with id: {}", chapterRecord.title(), storyId);
            return updatedStory.getId();
        }).orElseThrow(() -> new RuntimeException("Story with id: " + storyId + " not found"));
    }

    public Optional<ChapterRecord> findChapterById(@Nonnull Long storyId, @Nonnull Long chapterId) {
        return storyRepository.findById(storyId)
                .flatMap(story -> story.getChapters().stream()
                        .filter(chapter -> chapter.getId().equals(chapterId))
                        .findFirst()
                        .map(chapter -> new ChapterRecord(chapter.getTitle(), chapter.getSequence(), chapter.getChunks().stream()
                                .map(Chunk::getContent)
                                .collect(Collectors.joining()))));
    }

    public int cleanupDatabase() {
        // Get alls story ids
        final List<Long> storyIds = storyRepository.findAll().stream()
                .map(Story::getId)
                .toList();
        // Delete all stories and return the number of stories deleted
        storyRepository.deleteAll();
        log.info("All stories cleared");
        return storyIds.size();
    }

    public boolean deleteStoryById(@Nonnull Long storyId) {
        if (storyRepository.existsById(storyId)) {
            storyRepository.deleteById(storyId);
            log.info("Story with id: {} deleted successfully", storyId);
            return true;
        } else {
            log.warn("Story with id: {} not found", storyId);
            return false;
        }
    }
}
