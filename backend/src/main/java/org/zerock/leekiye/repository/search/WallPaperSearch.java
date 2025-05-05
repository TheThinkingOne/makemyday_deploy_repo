package org.zerock.leekiye.repository.search;

import org.springframework.data.domain.Page;
import org.zerock.leekiye.domain.WallPaper;
import org.zerock.leekiye.dto.PageRequestDTO;
import org.zerock.leekiye.dto.PageResponseDTO;
import org.zerock.leekiye.dto.WallPaperDTO;

public interface WallPaperSearch {
    PageResponseDTO<WallPaperDTO> searchWallpaperList(PageRequestDTO pageRequestDTO);
}
