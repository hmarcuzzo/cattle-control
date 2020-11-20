package br.com.cattle_control.starter.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.cattle_control.starter.model.Cattle;
import br.com.cattle_control.starter.model.Farm;
import br.com.cattle_control.starter.service.CattleService;
import br.com.cattle_control.starter.service.FarmService;
import lombok.RequiredArgsConstructor;

@Component
@RequestScope
@RequiredArgsConstructor
@Named
@RequestScoped
public class ChartView {

    @Autowired
    private final CattleService cattleService;

    @Autowired
    private final FarmService farmService;
 
    private PieChartModel pieModel1;
 
    @PostConstruct
    public void init() {
        createPieModels();
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
 
    private void createPieModels() {
        createPieModel1();
    }
 
    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        List<Farm> allFarms = farmService.readAll();

        for (Farm farm : allFarms) {
            List<Cattle> allCattleFarm = cattleService.findAllCattleFromFarm(farm.getRegisterNumber());
            
            int len = 0;
            float avrg_weight = 0;
            for (Cattle cattleFarm : allCattleFarm) {
                len++;
                avrg_weight += cattleFarm.getWeight();
            }

            if(len > 0) {
                avrg_weight = (avrg_weight / len);  
                pieModel1.set(farm.getName(), avrg_weight);
            }
        }
 
        pieModel1.setTitle("Média do peso do boi de cada fazenda.");
        // pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }
}
