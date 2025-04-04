package museon_online.astor_butler.telegram.handler;


import museon_online.astor_butler.telegram.utils.AstorUpdate;

public interface AstorBotHandler {
    boolean appliesTo(AstorUpdate update);
    void handle(AstorUpdate update);
}
