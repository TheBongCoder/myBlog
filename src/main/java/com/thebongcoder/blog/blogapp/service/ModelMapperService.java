package com.thebongcoder.blog.blogapp.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ModelMapperService {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public <T> T map(Object source, Class<T> className) {
        T t = null;
        try {
            t = modelMapper.map(source, className);
        } catch (Exception e) {
            log.error("Error while mapping", e);
        }
        return t;
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outClass) {
        return entityList.stream().map(entity -> map(entity, outClass)).collect(Collectors.toList());
    }


    public <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

}
