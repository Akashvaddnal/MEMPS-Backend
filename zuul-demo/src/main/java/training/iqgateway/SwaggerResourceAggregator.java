package training.iqgateway;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class SwaggerResourceAggregator implements SwaggerResourcesProvider {

    @Autowired
    private ZuulProperties zuulProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        Map<String, ZuulProperties.ZuulRoute> routes = zuulProperties.getRoutes();
        for (String routeName : routes.keySet()) {
            resources.add(swaggerResource(routeName, "/" + routeName + "/v2/api-docs", "2.0"));
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
