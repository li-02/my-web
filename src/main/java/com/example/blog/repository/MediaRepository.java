package com.example.blog.repository;

import com.example.blog.entity.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    /**
     * 根据文件名查询媒体文件
     */
    Optional<Media> findByFilename(String filename);

    /**
     * 根据文件路径查询媒体文件
     */
    Optional<Media> findByFilePath(String filePath);

    /**
     * 根据媒体类型查询文件
     */
    List<Media> findByMediaTypeOrderByUploadTimeDesc(String mediaType);

    /**
     * 分页查询媒体文件
     */
    Page<Media> findAllByOrderByUploadTimeDesc(Pageable pageable);

    /**
     * 根据原始文件名模糊查询
     */
    List<Media> findByOriginalFilenameContainingOrderByUploadTimeDesc(String originalFilename);

    /**
     * 查询指定大小范围的文件
     */
    List<Media> findByFileSizeBetweenOrderByUploadTimeDesc(Long minSize, Long maxSize);

    /**
     * 查询所有图片类型的媒体文件
     */
    @Query("SELECT m FROM Media m WHERE m.mediaType LIKE 'image/%' ORDER BY m.uploadTime DESC")
    List<Media> findAllImages();

    /**
     * 统计不同媒体类型的文件数量
     */
    @Query("SELECT m.mediaType, COUNT(m) FROM Media m GROUP BY m.mediaType")
    List<Object[]> countByMediaType();

    /**
     * 查询最新上传的文件
     */
    List<Media> findTop10ByOrderByUploadTimeDesc();

    /**
     * 计算总存储大小
     */
    @Query("SELECT SUM(m.fileSize) FROM Media m")
    Long getTotalStorageSize();

    /**
     * 根据文件名前缀查询
     */
    @Query("SELECT m FROM Media m WHERE m.filename LIKE :prefix% ORDER BY m.uploadTime DESC")
    List<Media> findByFilenamePrefix(@Param("prefix") String prefix);
}