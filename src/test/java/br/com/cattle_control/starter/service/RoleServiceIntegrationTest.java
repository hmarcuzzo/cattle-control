package br.com.cattle_control.starter.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.cattle_control.starter.model.Role;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoleServiceIntegrationTest {
    @Autowired
    RoleService roleService;


    @DisplayName("Testar a criação de um papel no BD.")
	@Test
	@Transactional
	@Rollback
	void testCreate() throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);

        assertThat(roleService.findByRoleName("Teste")).isEqualTo(role);
    }

    @DisplayName("Testar a procura de papel por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByRoleName() throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);
        
        assertThat(roleService.findByRoleName("Teste").getName()).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura de papel por Nome com letras minusculas.")
	@Test
	@Transactional
	@Rollback
	void testFindByRoleNameWithLowerCase() throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);
   
        assertThat(roleService.findByRoleName("teste").getName()).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura de papéis por Nome com letras maisculas.")
	@Test
	@Transactional
	@Rollback
	void testFindByRoleNameWithUpperCase() throws Exception{
        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);
       
        assertThat(roleService.findByRoleName("TESTE").getName()).isEqualTo("Teste");
    }

    @DisplayName("Testar o update de um papel no BD.")
	@Test
	@Transactional
	@Rollback
	void testUpdateRole () throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);
       
        Integer id = roleService.findByRoleName("Teste").getId();

        role = Role.builder()
                            .id(id)
                            .name("Teste2")
                            .deleted(false)
                            .build();

        roleService.update(role);

        
        assertThat(roleService.findByRoleName("Teste2")).isEqualTo(role);
    }

    @DisplayName("Testar a procura de um papel deletado por Nome.")
	@Test
	@Transactional
	@Rollback
	void testFindByRoleNameDeleted() throws Exception {

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(true)
                            .build();

        roleService.create(role);

        assertThrows(com.github.adminfaces.template.exception.BusinessException.class, () -> {
            roleService.findByRoleName("Teste");
        });
    }

    @DisplayName("Testar a criação de um papel com nome igual à um já deletado.")
	@Test
	@Transactional
	@Rollback
	void testCreateRoleNameEqualsDeleted() throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(true)
                            .build();

        roleService.create(role);
        
        role = Role.builder()
                    .name("Teste")
                    .deleted(false)
                    .build();

        roleService.create(role);

        assertThat(roleService.findByRoleName("Teste")).isEqualTo(role);
    }


    @DisplayName("Testar a procura por nome de papéis existentes.")
	@Test
	@Transactional
	@Rollback
	void testGetRoleNames() throws Exception{
        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);

        assertThat(roleService.getRoleNames("Tes").get(0)).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura por nome de papéis existentes em UpperCase.")
	@Test
	@Transactional
	@Rollback
	void testGetRoleNamesWithUpperCase() throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);

        assertThat(roleService.getRoleNames("TES").get(0)).isEqualTo("Teste");
    }

    @DisplayName("Testar a procura por nome de papéis existentes em LowerCase.")
	@Test
	@Transactional
	@Rollback
	void testGetRoleNamesWithLowerCase() throws Exception{

        Role role = Role.builder()
                            .name("Teste")
                            .deleted(false)
                            .build();

        roleService.create(role);

        assertThat(roleService.getRoleNames("tes").get(0)).isEqualTo("Teste");
    }

}
