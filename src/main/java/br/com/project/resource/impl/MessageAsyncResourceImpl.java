package br.com.project.resource.impl;

import br.com.project.resource.MessageAsyncResource;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageAsyncResourceImpl implements MessageAsyncResource {

    @Override
    public Uni<String> greeting(String name) {
        return null;
    }

}
