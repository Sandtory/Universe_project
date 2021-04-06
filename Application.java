package no.hiof.henninsa.oblig5;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.rendering.vue.VueComponent;
import no.hiof.henninsa.oblig5.controller.PlanetController;
import no.hiof.henninsa.oblig5.controller.PlanetSystemController;
import no.hiof.henninsa.oblig5.repository.UniverseCSVRepository;
import no.hiof.henninsa.oblig5.repository.UniverseDataRepository;
import no.hiof.henninsa.oblig5.repository.UniverseJSONRepository;
import no.hiof.henninsa.oblig5.repository.UniverseRepository;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start();

        app.config.enableWebjars();

        app.get("/hello-world", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                ctx.result("Hello, World!");
            }
        });

        app.before("/", ctx -> ctx.redirect("/planet-system"));

        app.get("/planet-system", new VueComponent("planet-system-overview"));
        app.get("/planet-system/:planet-system-id", new VueComponent("planet-system-detail"));
        app.get("/planet-system/:planet-system-id/planets/:planet-id", new VueComponent("planet-detail"));

        UniverseRepository universeRepository = new UniverseCSVRepository("planets_100.csv");
        PlanetSystemController planetSystemController = new PlanetSystemController(universeRepository);
        PlanetController planetController = new PlanetController(universeRepository);

        app.get("api/planet-system", new Handler() {
            @Override
            public void handle(@NotNull Context ctx) throws Exception {
                planetSystemController.getAllPlanetSystems(ctx);
            }
        });
        app.get("/api/planet-system/:planet-system-id", planetSystemController::getPlanetSystem);
        app.get("/api/planet-system/:planet-system-id/planets", ctx -> planetController.getPlanets(ctx));
        app.get("/api/planet-system/:planet-system-id/planets/:planet-id", ctx -> planetController.getPlanet(ctx));
    }
}
