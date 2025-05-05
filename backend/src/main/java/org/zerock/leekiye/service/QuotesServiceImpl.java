package org.zerock.leekiye.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.zerock.leekiye.domain.Quotes;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.QuotesDTO;
import org.zerock.leekiye.repository.QuotesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class QuotesServiceImpl implements QuotesService {

    private final QuotesRepository quotesRepository;

    // 해당 인용구가 뭔지 보긴 해야하니 get 이 필요할듯
    @Override
    public QuotesDTO get(Long qno) {
        Optional<Quotes> result = quotesRepository.findById(qno);

        Quotes quotes = result.orElseThrow();

        return entityToDTO(quotes);
    }

    @Override
    public Long register(QuotesDTO quotesDTO) {

        Quotes quotes = dtoToEntity(quotesDTO);

        return quotesRepository.save(quotes).getQno();
    }

    //
    @Override
    public PageResponseDTO<QuotesDTO> getList(PageRequestDTO pageRequestDTO) {

        Page<Quotes> result = quotesRepository.quotesSearch(pageRequestDTO);

        List<QuotesDTO> dtoList = result
                .get()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        PageResponseDTO<QuotesDTO> responseDTO =
                PageResponseDTO
                        .<QuotesDTO>withAll()
                        .dtoList(dtoList)
                        .pageRequestDTO(pageRequestDTO)
                        .totalCount(result.getTotalElements())
                        .build();

        log.info("Quotes 의 ResponseDTO : " + responseDTO);
        return responseDTO;
    }

    @Override
    public void remove(Long qno) {
        quotesRepository.deleteById(qno);
    }

    // 여기서 직접 dtoToEntity 구현 안하고 그냥 super 로 했더니 Entity Null 오류 발생하고 있었음
    @Override
    public Quotes dtoToEntity(QuotesDTO quotesDTO) {

        return Quotes.builder()
                .quotes(quotesDTO.getQuotes())
                .author(quotesDTO.getAuthor())
                .build();
    }

    @Override
    public QuotesDTO entityToDTO(Quotes quotes) {
        return QuotesService.super.entityToDTO(quotes);
    }

    @Override
    public QuotesDTO getRandomQuote() {
        long count = quotesRepository.count();
        if (count == 0) throw new RuntimeException("등록된 명언이 없습니다.");

        long randomIndex = (long)(Math.random() * count);
        List<Quotes> list = quotesRepository.findAll(PageRequest.of((int) randomIndex, 1)).getContent();

        return entityToDTO(list.get(0));

    }
}
