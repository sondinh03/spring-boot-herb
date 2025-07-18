package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "research_purchase")
@Builder
public class ResearchPurchase extends BaseEntity {
    private Long userId;
    private Long researchId;
}
