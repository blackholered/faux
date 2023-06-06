package com.it355.backend.service.impl;

import com.it355.backend.dto.VideoFileDTO;
import com.it355.backend.entity.User;
import com.it355.backend.entity.Video;
import com.it355.backend.exception.impl.NoElementException;
import com.it355.backend.repository.VideoRepository;
import com.it355.backend.service.VideoService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
@Service
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class VideoServiceImpl implements VideoService {

    @Value("${cdn.directory}")
    private String cdnDirectory;

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Value("${ffprobe.path}")
    private String ffprobePath;

    private final VideoRepository videoRepository;


    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Page<Video> findPaginated(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public List<Video> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable) {
        return videoRepository.findByNameContainingOrDescriptionContaining(name, description, pageable);
    }

    @Override
    public Video findById(Integer id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new NoElementException("Video is not found"));
    }

    @Override
    public Video save(VideoFileDTO videoFileDTO, User user) throws IOException {

        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        int sec = 15;
        FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
        FFprobe ffprobe = new FFprobe(ffprobePath);


        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        long timeOffset = TimeUnit.SECONDS.toMillis(sec);

        File tempFile = File.createTempFile("video", ".mp4"); // Create a temporary file

        videoFileDTO.getFile().transferTo(tempFile);

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(tempFile.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(cdnDirectory + fileName + ".png")
                .setFrames(1)
                .setStartOffset(timeOffset, TimeUnit.MILLISECONDS)
                .done();
        executor.createJob(builder).run();

        Path sourcePath = tempFile.toPath();
        Path destinationPath = Paths.get(cdnDirectory, fileName + ".mp4");
        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);


        Video video = new Video();
        video.setName(videoFileDTO.getName());
        video.setDescription(videoFileDTO.getDescription());
        video.setFile(fileName);


        video.setUser(user);

        return videoRepository.save(video);
    }

    @Override
    public List<Video> findByUser(User user) {
        return videoRepository.findByUser(user);
    }

    @Override
    public Video findByUserIdAndId(Integer userId, Integer id) {
        return videoRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new NoElementException("Video is not found"));
    }

    @Override
    public Video update(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void delete(Video video) {
        videoRepository.delete(video);
    }


}
