package com.github.aetherwisp.volvox.domain.user;

import com.github.aetherwisp.volvox.domain.Entity;

public final class GeneralUser extends AbstractUser<GeneralUser> implements Entity<GeneralUser> {
    private static final long serialVersionUID = 4128472505610227444L;

    //======================================================================
    // Constructors
    private GeneralUser(final Builder _builder) {
        super(_builder);
    }

    //======================================================================
    // Methods
    @Override
    public boolean sameAs(GeneralUser _other) {
        return null != _other
                ? this.getId()
                    .equals(_other.getId())
                : false;
    }

    //======================================================================
    // Classes
    public static class Builder extends AbstractUserBuilder<GeneralUser> {
        @Override
        public GeneralUser build() {
            return new GeneralUser(this);
        }
    }
}
