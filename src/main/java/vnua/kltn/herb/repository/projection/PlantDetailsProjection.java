package vnua.kltn.herb.repository.projection;

import vnua.kltn.herb.entity.ActiveCompound;
import vnua.kltn.herb.entity.DataSource;
import vnua.kltn.herb.entity.Plant;

public interface PlantDetailsProjection {
    Plant getPlant();
    DataSource getDataSource();
    ActiveCompound getActiveCompound();
}
