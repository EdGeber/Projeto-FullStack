package projeto_fullstack.api.resource.empresa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import projeto_fullstack.api.entity.Empresa;
import projeto_fullstack.api.entity.Fornecimento;
import projeto_fullstack.api.error.ErrorObject;
import projeto_fullstack.api.repository.EmpresaRepository;
import projeto_fullstack.api.repository.FornecimentoRepository;
import projeto_fullstack.api.repository.FornecimentoService;

@RestController
@RequestMapping(path = "/empresa")
@Slf4j
@CrossOrigin // apagar isso em producao. Deixar apenas para desenvolvimento
public class EmpresaResource {
	@Autowired
	MessageSource messageSource;
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	FornecimentoRepository fornecimentoRepository;
	@Autowired
	FornecimentoService fornecimentoService;

	Boolean isCnpjUnique(Empresa empresa) {
		List<Empresa> empresas = empresaRepository.findByCnpj(empresa.getCnpj());
		return (empresas == null) || (empresas.size() == 0) || (!empresas.get(0).getCnpj().equals(empresa.getCnpj()))
				|| (empresas.get(0).getId() == empresa.getId());
	}

	ResponseEntity<Object> getCnpjExistsErrorResponse() {
		Map<String, String> fieldErrors = new HashMap<>();
		List<String> businessErrors = new ArrayList<String>();
		// TODO usar locale da requisicao
		fieldErrors.put("cnpj", messageSource.getMessage("cnpjExistsEmpresa", null, Locale.of("PT")));
		return new ResponseEntity<>(new ErrorObject(fieldErrors, businessErrors), HttpStatus.BAD_REQUEST);
	}

	@ResponseBody
	@PostMapping(path = "")
	// PostEmpresaResponse if no error
	public ResponseEntity<Object> create(@Valid @RequestBody PostEmpresaRequest reqObj) {
		Empresa empresa = reqObj.getEmpresa();

		if(!isCnpjUnique(empresa)) {
			return getCnpjExistsErrorResponse();
		}

		Long id = empresaRepository.save(empresa).getId();

		List<Long> fornecedorIds = reqObj.getFornecedorIds();
		List<Long> fornecimentoIds = null;
		if (fornecedorIds != null) {
			fornecimentoIds = addFornecedores(id, fornecedorIds);
		}

		return new ResponseEntity<>(new PostEmpresaResponse(id, fornecimentoIds), HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(path = "{id}/fornecimentos")
	public List<Long> addFornecedores(@PathVariable("id") Long id, @RequestBody List<Long> fornecedorIds) {
		if (!empresaRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		List<Fornecimento> fornecimentos = new ArrayList<>();
		for (Long fornecedorId : fornecedorIds) {
			Fornecimento fornecimento = new Fornecimento();
			fornecimento.setEmpresaId(id);
			fornecimento.setFornecedorId(fornecedorId);
			fornecimentos.add(fornecimento);
		}
		fornecimentos = fornecimentoRepository.saveAll(fornecimentos);
		return fornecimentos.stream().map(f -> f.getId()).collect(Collectors.toList());
	}

	@ResponseBody
	@PutMapping(path = "{id}")
	// empty response if no error
	public ResponseEntity<Object> put(@PathVariable("id") Long id, @Valid @RequestBody Empresa empresa) {
		if (id != empresa.getId()) {
			Map<String, String> fieldErrors = new HashMap<>();
			List<String> businessErrors = new ArrayList<String>();
			// TODO usar locale da requisicao
			businessErrors.add(messageSource.getMessage("idsDontMatch", null, Locale.of("PT")));
			return new ResponseEntity<>(new ErrorObject(fieldErrors, businessErrors), HttpStatus.BAD_REQUEST);
		}
		
		if(!isCnpjUnique(empresa)) {
			return getCnpjExistsErrorResponse();
		}
		
		empresa = empresaRepository.save(empresa);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(path = "{id}")
	public GetEmpresaResponse findById(@PathVariable("id") Long id,
			@RequestParam(name = "fornecedores", required = false, defaultValue = "false") boolean fornecedores) {
		Optional<Empresa> result = empresaRepository.findById(id);
		if (result.isPresent()) {
			Empresa empresa = result.get();
			List<Map<String, Object>> fornecimentos = null;
			if (fornecedores) {
				fornecimentos = fornecimentoService.getFornecedores(id);
			}
			return new GetEmpresaResponse(empresa, fornecimentos);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	// isso fica lento se a tabela ficar muito grande
	@ResponseBody
	@GetMapping(path = "count")
	public Long getCount() {
		return empresaRepository.count();
	}

	@ResponseBody
	@GetMapping(path = "page")
	public Iterable<Empresa> getPage(
			@RequestParam(name = "from", required = false, defaultValue = ValueConstants.DEFAULT_NONE) String from) {
		final int PAGE_SIZE = 10;
		if (from == null) {
			return empresaRepository.getFirstPage(PAGE_SIZE);
		} else {
			return empresaRepository.getPage(from, PAGE_SIZE);
		}
	}

	@ResponseBody
	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable("id") Long id) {
		if (!empresaRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		empresaRepository.deleteById(id);
	}
}
