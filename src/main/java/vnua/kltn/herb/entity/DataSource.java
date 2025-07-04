package vnua.kltn.herb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=true)
@Data
@Entity
@Table(name = "data_sources")
@NoArgsConstructor
@AllArgsConstructor
public class DataSource extends BaseEntity {
    /**
     * Tên của nguồn dữ liệu (sách, tạp chí, website, v.v.)
     */
    @Column(nullable = false)
    private String name;

    /**
     * Chuỗi định danh duy nhất (slug) của nguồn dữ liệu, dùng để tạo URL thân thiện
     */
    @Column(nullable = false, unique = true)
    private String slug;

    /**
     * Loại nguồn dữ liệu (book, journal, website, other)
     */
    @Column(nullable = false)
    private Integer typeSource;

    /**
     * Mô tả chi tiết về nguồn dữ liệu
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Tác giả hoặc nhóm tác giả của nguồn dữ liệu (nếu có)
     */
    @Column(name = "author")
    private String author;

    /**
     * Nhà xuất bản của nguồn dữ liệu (dành cho sách/tạp chí)
     */
    @Column(name = "publisher")
    private String publisher;

    /**
     * Năm xuất bản của nguồn dữ liệu
     */
    @Column(name = "publication_year")
    private Integer publicationYear;

    /**
     * Đường dẫn URL của nguồn dữ liệu (dành cho website)
     */
    @Column(name = "url")
    private String url;

    /**
     * Số ISBN (sách) hoặc ISSN (tạp chí)
     */
    @Column(name = "isbn_issn")
    private String isbnIssn;
}
