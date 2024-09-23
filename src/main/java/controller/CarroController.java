package controller;

import routes.CarroRoutes;
import static spark.Spark.*;

public class CarroController {
    private final CarroRoutes carroRoutes = new CarroRoutes();

    public void respostasRequisicoes() {
        get("/carros", carroRoutes::index);
        get("/carros/:id", carroRoutes::show);
        delete("/carros/:id", carroRoutes::delete);
        post("/carros", carroRoutes::create);
        put("/carros", carroRoutes::update);
    }
}