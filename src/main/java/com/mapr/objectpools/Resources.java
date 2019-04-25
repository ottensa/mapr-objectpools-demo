package com.mapr.objectpools;

import com.mapr.objectpools.model.Cluster;
import com.mapr.objectpools.service.MaprService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Resources {

    @Autowired
    MaprService maprService;

    @Bean
    public List<Cluster> clusters() throws Exception {
        return maprService.getClusters();
        //return Arrays.asList(new Cluster("objectpools.mapr.com"));
        /*
        return Files.lines(Paths.get("/opt/mapr/conf/mapr-clusters.conf"))
                .map(line -> new Cluster( line.split(" ")[0] ))
                .collect(Collectors.toList());
        */
    }

}
