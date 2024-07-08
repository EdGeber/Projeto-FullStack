package projeto_fullstack.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;

import projeto_fullstack.api.repository.EmpresaRepository;
import projeto_fullstack.api.repository.FornecedorRepository;
import projeto_fullstack.api.repository.FornecimentoRepository;
import projeto_fullstack.api.resource.empresa.EmpresaResource;
import projeto_fullstack.api.resource.fornecedor.FornecedorResource;

@SpringBootApplication
public class ProjetoFullstackApiApplication {
	@Autowired
	public static WebClient webClient = WebClient.create();
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private FornecedorRepository forncedorRepository;
	@Autowired
	private FornecimentoRepository fornecimentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoFullstackApiApplication.class, args);
	}
	
	@EventListener(ContextRefreshedEvent.class)
	public void setStaticBeans() {
	    EmpresaResource.empresaRepository = empresaRepository;
	    EmpresaResource.fornecimentoRepository = fornecimentoRepository;
	    FornecedorResource.fornecedorRepository = forncedorRepository;
	    FornecedorResource.fornecimentoRepository = fornecimentoRepository;
	}

}
