package ua.pytaichuk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.pytaichuk.models.Group;
import ua.pytaichuk.repositories.GroupRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GroupsService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupsService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll(){
        return groupRepository.findAll();
    }

    public Group findById(int id){
        return groupRepository.findById(id).orElse(null);
    }
}
