package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.*;
import org.apache.commons.compress.utils.Sets;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component("mediaController")
@ManagedBean
@ViewScoped
public class MediaController {
    private JpaRepository<OnDemand> repo;
    private List<MediaDTO> mediaList;

    public MediaController(EntityManager em) {
        repo = new JpaRepository<>(em, OnDemand.class);
    }

    public void load() {
        var list = repo.findAll();

        mediaList = list.stream().map(m -> {

            var dto = new MediaDTO();
            dto.setId(m.getId());
            if (m.getOwner().isEmpty())dto.setOwner(null);
            else dto.setOwner(new ArrayList<>(m.getOwner()).get(0));

            dto.setRatings(m.getRatings().stream().mapToInt(Rating::getRating).toArray());
            dto.setTitle(m.getTitle());
            dto.setCreatedAt(m.getPublishDate());

            if (m instanceof Movie)
                dto.setType(MediaType.MOVIE);
            else if (m instanceof Document)
                dto.setType(MediaType.DOCUMENT);
            else if (m instanceof Audio)
                dto.setType(MediaType.AUDIO);


            return dto;
        }).collect(Collectors.toList());
    }
    public double avgRatings(int[] ratings){
        return Arrays.stream(ratings).average().orElse(3.0);
    }

    public List<MediaDTO> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaDTO> mediaList) {
        this.mediaList = mediaList;
    }

    @Transactional
    public void onRowEdit(RowEditEvent<MediaDTO> event) {
        //repo.update(event.getObject());       //difficult
        var obj=repo.findById(event.getObject().getId()).get();

        obj.setOwner(Sets.newHashSet(event.getObject().getOwner()));        //may be null

        repo.update(obj);
        FacesMessage msg = new FacesMessage("Erfolgreich bearbeitet", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    @Transactional
    public void deleteMedia(MediaDTO dto){
        repo.delete(dto.getId());
        mediaList.remove(dto);

    }
}
