package org.zerock.leekiye.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.zerock.leekiye.domain.Todo;
import org.zerock.leekiye.dto.PageRequestDTO;

public interface TodoSearch {
    Page<Todo> todoSearchByUser(PageRequestDTO pageRequestDTO, String userID);

}
