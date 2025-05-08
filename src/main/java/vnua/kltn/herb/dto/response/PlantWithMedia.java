package vnua.kltn.herb.dto.response;

import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantMedia;

public interface PlantWithMedia {
    Plant getPlant();
    PlantMedia getMedia();
}
