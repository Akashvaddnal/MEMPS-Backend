package training.iqgateway;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class SwaggerResourcesConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("System-Admin-MS", "/System-Admin-MS/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Login-Auth-MS", "/Login-Auth-MS/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Inventory-Manager-MS", "/Inventory-Manager-MS/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Procurement-Officer-MS", "/Procurement-Officer-MS/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Hospital-Staff-MS", "/Hospital-Staff-MS/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Biomedical-and-Clinical-Engineer-MS", "/Biomedical-and-Clinical-Engineer-MS/v2/api-docs", "2.0"));
        resources.add(swaggerResource("Financial-and-Accounting-Team-MS", "/Financial-and-Accounting-Team-MS/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource resource = new SwaggerResource();
        resource.setName(name);
        resource.setLocation(location);
        resource.setSwaggerVersion(version);
        return resource;
    }
}
