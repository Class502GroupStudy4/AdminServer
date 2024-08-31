package org.g9project4.publicData.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.g9project4.publicData.events.PublicDataDoneEvent;
import org.g9project4.publicData.events.PublicDataStartEvent;
import org.g9project4.publicData.tour.services.ApiUpdateService;
import org.g9project4.publicData.tourvisit.services.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublicDataEventListener {
    private final PublicDataUpdateService updateService;
    private final ApiUpdateService apiUpdateService;
    private final SidoVisitStatisticService sidoVisitStatisticService;
    private final SigunguVisitStatisticService sigunguVisitStatisticService;
    private final SigunguTableStatisticService sigunguTableStatisticService;
    private final VisitUpdateService visitUpdateService;
    private final TourplacePointService tourplacePointService;

    @Async
    @EventListener
    public void apiUpdate(PublicDataStartEvent event) throws InterruptedException{
        log.info("이벤트 수신, 작업명: {} 시작", event.getWorkNm());

        // 작업 코드 추가
        if(event.getWorkNm() == null){
            return;
        }else if(event.getWorkNm().equals("tour")){
            apiUpdateService.update(event.getServiceKey());
        }else if(event.getWorkNm().equals("green")){
            apiUpdateService.greenUpdate(event.getServiceKey());
        }else if(event.getWorkNm().equals("areaCode")){
            apiUpdateService.areaCodeUpdate(event.getServiceKey());
        } else if (event.getWorkNm().equals("categories")) {
            apiUpdateService.categoryUpdate(event.getServiceKey());
        } else if (event.getWorkNm().equals("sidoVisit")){
            sidoVisitStatisticService.updateSidoVisit("1D");
            sidoVisitStatisticService.updateSidoVisit("1W");
            sidoVisitStatisticService.updateSidoVisit("1M");
            sidoVisitStatisticService.updateSidoVisit("3M");
            sidoVisitStatisticService.updateSidoVisit("6M");
            sidoVisitStatisticService.updateSidoVisit("1Y");
        } else if (event.getWorkNm().equals("sigunguVisit")) {
            sigunguVisitStatisticService.updateVisit("1D");
            sigunguVisitStatisticService.updateVisit("1W");
            sigunguVisitStatisticService.updateVisit("1M");
            sigunguVisitStatisticService.updateVisit("3M");
            sigunguVisitStatisticService.updateVisit("6M");
            sigunguVisitStatisticService.updateVisit("1Y");
        }else if (event.getWorkNm().equals("table")) {
            sigunguTableStatisticService.update();
        }else if (event.getWorkNm().equals("visitUpdate")) {
            visitUpdateService.update();
        }else if (event.getWorkNm().equals("point")) {
            tourplacePointService.update();
        }
        // 작업 끝나면 이벤트 발생 시켜 후속 작업 진행
        updateService.end();
    }

    @Async
    @EventListener
    public void update2(PublicDataDoneEvent event) {
        log.info("이벤트 수신, 작업명: {} 종료", event.getWorkNm());

        // 종료 관련 코드 처리 ...
    }
}
