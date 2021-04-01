package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.Media;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Component("mediaController")
@ManagedBean
@ViewScoped
public class MediaController {
    private IRepository<Media, Long> repo;
    private List<MediaDTO> mediaList;

    public MediaController(EntityManager em){
        repo=new JpaRepository<>(em, Media.class);
    }
    public void load(){
        var list=repo.findAll();

        mediaList=list.stream().map(m->{

            var dto=new MediaDTO();

            return dto;
        }).collect(Collectors.toList());
    }

    public List<MediaDTO> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaDTO> mediaList) {
        this.mediaList = mediaList;
    }
}
