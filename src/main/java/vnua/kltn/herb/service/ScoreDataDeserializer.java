package vnua.kltn.herb.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import vnua.kltn.herb.entity.ScoreData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScoreDataDeserializer extends JsonDeserializer<Map<String, ScoreData>> {

    @Override
    public Map<String, ScoreData> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        JsonNode node = p.getCodec().readTree(p);

        // ⭐ Nếu là Array [] → Trả về Map rỗng
        if (node.isArray()) {
            return new HashMap<>();
        }

        // ⭐ Nếu là Object {} → Parse bình thường
        if (node.isObject()) {
            Map<String, ScoreData> result = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            node.fields().forEachRemaining(entry -> {
                try {
                    String key = entry.getKey();
                    ScoreData value = mapper.treeToValue(entry.getValue(), ScoreData.class);
                    result.put(key, value);
                } catch (Exception e) {
                    System.err.println("Error parsing score data for key: " + entry.getKey());
                    e.printStackTrace();
                }
            });

            return result;
        }

        // ⭐ Nếu là null → Trả về Map rỗng
        return new HashMap<>();
    }
}
