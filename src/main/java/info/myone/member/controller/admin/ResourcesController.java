package info.myone.member.controller.admin;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import info.myone.member.domain.dto.ResourcesDto;
import info.myone.member.domain.entity.Resources;
import info.myone.member.domain.entity.Role;
import info.myone.member.domain.entity.RoleResources;
import info.myone.member.repository.RoleRepository;
import info.myone.member.service.ResourcesService;
import info.myone.member.service.RoleService;

@Controller
public class ResourcesController {
	
	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@GetMapping(value="/admin/resources")
	public String getResources(Model model) throws Exception {

		List<Resources> resources = resourcesService.getResources();
		model.addAttribute("resources", resources);

		return "admin/resource/list";
	}

	@PostMapping(value="/admin/resources")
	public String createResources(ResourcesDto resourcesDto) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());

		Resources resources = modelMapper.map(resourcesDto, Resources.class);
		Set<RoleResources> roleResources = new HashSet<>();
		roleResources.add(RoleResources.builder().role(role).resources(resources).build());
		resources.setRoleSet(roleResources);

		resourcesService.createResources(resources);

		return "redirect:/admin/resources";
	}

	@GetMapping(value="/admin/resources/register")
	public String viewRoles(Model model) throws Exception {

		List<Role> roleList = roleService.getRoles();
		model.addAttribute("roleList", roleList);

		ResourcesDto resources = new ResourcesDto();
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(new Role());
		resources.setRoleSet(roleSet);
		model.addAttribute("resources", resources);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resources/{id}")
	public String getResources(@PathVariable String id, Model model) throws Exception {

		List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);
		Resources resources = resourcesService.getResources(Long.valueOf(id));

		ModelMapper modelMapper = new ModelMapper();
		ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);
		model.addAttribute("resources", resourcesDto);

		return "admin/resource/detail";
	}

	@GetMapping(value="/admin/resources/delete/{id}")
	public String removeResources(@PathVariable String id, Model model) throws Exception {

		Resources resources = resourcesService.getResources(Long.valueOf(id));
		resourcesService.deleteResources(Long.valueOf(id));

		return "redirect:/admin/resources";
	}
}
