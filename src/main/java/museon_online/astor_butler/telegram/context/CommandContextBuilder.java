package museon_online.astor_butler.telegram.context;

import museon_online.astor_butler.telegram.utils.AstorUpdate;

public interface CommandContextBuilder {
    CommandContext build(AstorUpdate update);
}