package vnua.kltn.herb.service;

import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.exception.HerbException;

import java.io.IOException;

public interface MediaService {
    MediaResponseDto upload(MediaRequestDto requestDto) throws HerbException, IOException;

    MediaResponseDto getById(Long id) throws HerbException;

    byte[] getData(Long id) throws HerbException, IOException;

    Boolean delete(Long id) throws HerbException;
}
