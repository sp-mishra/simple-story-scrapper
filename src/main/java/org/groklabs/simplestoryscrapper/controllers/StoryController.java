package org.groklabs.simplestoryscrapper.controllers;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.groklabs.simplestoryscrapper.dtos.ChapterRecord;
import org.groklabs.simplestoryscrapper.dtos.StoryRecord;
import org.groklabs.simplestoryscrapper.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/story")
@Slf4j
public class StoryController {
    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("/addStory")
    public ResponseEntity<String> addStory(@RequestBody StoryRecord storyRecord) {
        return ResponseEntity.ok(storyService.addStory(storyRecord));
    }

    @PutMapping("/updateStory/{storyId}")
    public ResponseEntity<String> updateStory(@PathVariable Long storyId, @RequestBody StoryRecord storyRecord) {
        try {
            String updatedStoryId = storyService.updateStory(storyId, storyRecord);
            return ResponseEntity.ok("Story with id: " + updatedStoryId + " updated successfully");
        } catch (RuntimeException e) {
            log.error("Error updating story: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error updating story: " + e.getMessage());
        }
    }

    @PostMapping("/{storyId}/chapters")
    public ResponseEntity<Long> updateOrInsertChapter(@PathVariable Long storyId, @RequestBody ChapterRecord chapterRecord) {
        final Long chapterId = storyService.updateOrInsertChapter(storyId, chapterRecord);
        return ResponseEntity.ok(chapterId);
    }

    @GetMapping("/{storyId}/chapters/{chapterId}")
    public ResponseEntity<ChapterRecord> findChapterById(@PathVariable @Nonnull Long storyId, @PathVariable @Nonnull Long chapterId) {
        Optional<ChapterRecord> chapterRecord = storyService.findChapterById(storyId, chapterId);
        return chapterRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/cleanupDatabase")
    public ResponseEntity<String> clear() {
        final int storiesDeleted = storyService.cleanupDatabase();
        final String message = storiesDeleted + " stories deleted";
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{storyId}")
    public ResponseEntity<String> deleteStory(@PathVariable Long storyId) {
        boolean deleted = storyService.deleteStoryById(storyId);
        if (deleted) {
            return ResponseEntity.ok("Story with id: " + storyId + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


