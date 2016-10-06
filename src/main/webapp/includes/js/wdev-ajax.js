$(document).ready(function() {

	/**
	 * Conecta um campo SELECT a uma fonte dados atraves de JSON
	 * Recebe os parametros:
		{
			url: "http://url-xyz/@{#id-do-input}/@{.class-do-input}/xx/@{paramName1}/yy/@{paramName2}"
			, params: { 
				"paramName1": "paramValue1"
				, "paramName2": function() {
					return "paramValue2"
				}
			}
		}
	**/
	var paramRE = /@\{([#\.\w\d-_]+)\}/g;
	
	$.Options = {
		Loading: "<option value=''>Aguarde ...</option>"
		, Default: ""
		, All: "<option value=''>Todos</option>"
		, Select: "<option value=''>Selecione</option>"
	};
	$.fn.connect = function(opts) {
		// Configura as opcoes default
		var userOptions = $.extend({defaultOptions: $.Options.Default // Opcoes fixas na combo apos retorno do AJAX
								  , loadingOption: $.Options.Loading  // Texto de aguarde durante o AJAX
								  , codeAttribute: 'codigo'
								  , labelAttribute: 'descricao'}, opts);
		
		// Para cada elemento encontrado tenta conectar a fonte de dados ao SELECT
		return this.each(function connect() {
			var $me = $(this);
			
			// Obtem as configuracoes para este elemento caso ja existam
			var options = $me.data('connect-options');
			// Caso nao exista nenhuma configuracao significa que e a primeira vez que executa essa funcao 
			var firstTime = options == null;
			// Caso as configuracoes nao existam, cria as configuracoes e armazena como "connect-options" para a proxima execucao dessa funcao.
			options = options || $.extend({}, userOptions);
			$me.data('connect-options', options);
			
			var url = options.url;
			var resolvedParameters = true;
			var hasOptions = $me[0].options.length > 0;
			
			// Adiciona os default options pela primeira vez
			if (firstTime && !hasOptions && options.defaultOptions) {
				$me[0].options.length = 0;
				$me.append(options.defaultOptions);
			}
			
			// Tenta montar a URL completa substituindo todos os parametros entre chaves {...}
			var newURL = url.replace(paramRE, function(match, parameterName) {
				// Se o par�metro comecar com @@ ent�o ...
				if (parameterName.length > 1 && parameterName[0] == '@' && parameterName[1] == '@') {
					// O nome real do parametro e sem @@ na frente
					var realParameterName = parameterName.substring(2);
					// Caso o parametro nao seja do jQuery, verificar se e uma funcao ou se e um valor
					var param = options.params && options.params[realParameterName];
					var value = $.type(param) == 'function' ? param(url, realParameterName, parameterName) : param;
					// Verificar se conseguiu obter este valor
					resolvedParameters &= $.type(value) != 'undefined';
					// Retorna o valor correto
					return value;
				} else { // Se o primeiro caracter do parametro for # ou ., considerar que seja um elemento do jQuery que eu dependo
					// Se for a primeira vez...
					if (firstTime) {
						// Faz o bind nesse campo para quando for alterado me chamar (esta funcao) novamente para verificar se a url foi alterada...
						$(parameterName).bind('change keyup', $.proxy(connect, $me));
					}
					// Obtem o valor selecionado no campo que eu dependo
					var value = $(parameterName).val();
					// Indica se conseguiu obter o valor do campo que eu dependo
					resolvedParameters &= value != null && value != "";
					// Retorna o valor correto para montar na url
					return value;
				}
			});
			
			// Se for a primeira vez e possui mais de uma opcao na tela, confiar que nao precisa fazer o AJAX nesse momento.
			if (firstTime && hasOptions) {
				return;
			}
			
			// Se todos os parametros foram encontrados para montar a URL, entao...
			if (resolvedParameters) {
				console.log("try connection #" + $me.attr('id'), "to", newURL);
				// Guarda o valor da opcao que esta selecionada no elemento
				var selectedValueBeforeAjax = $me.val();
				
				// Limpa o select e adiciona a opcao com o texto de aguarde/carregando...  
				$me[0].options.length = 0;
				$me.append(options.loadingOption);
				
				// Chama o evento "waiting.connect" (aguardando conexao com fonte de dados)
				$me.trigger('waiting.connect');
				
				// Chama o AJAX para carregar as novas opcoes
				$.getJSON(newURL, function(list) {
					// Cria os elementos options numa string
					var optionsHtmlString = $.map(list, function(object, index) {
						return "<option value='" + object[options.codeAttribute] + "'>" + object[options.labelAttribute] + "</option>";
					}).join("");
					
					// Apaga as opcoes do select box
					$me[0].options.length = 0;
					
					// Cria as opcoes defaults
					$me.append($(options.defaultOptions));
					
					// Cria as opcoes retornadas pelo AJAX
					$me.append($(optionsHtmlString));
					
					// Seleciona a opcao que estava marcada antes do AJAX
					$me.val(selectedValueBeforeAjax);
					
					// Se o valor anterior nao for igual ao valor retornado (forcar on change neste campo)
					if (selectedValueBeforeAjax != $me.val()) {
						$me.trigger('change');
					}
					
					// Executa o evento "loaded.connect" (conexao com fonte de dados carregada)
					$me.trigger('loaded.connect');
					$me.trigger('change.connect');
				});
			} else {
				console.log("connection not ready #" + $me.attr('id'), "to", options.url, "as", newURL);
				
				// Apaga as opcoes do select box
				$me[0].options.length = 0;
				
				// Cria as opcoes defaults
				$me.append($(options.defaultOptions));
				
				// Executa o evento "not.ready.connect" (as dependencias ainda nao estao prontas para conectar com a fonte de dados)
				$me.trigger('not.ready.connect');
				$me.trigger('change.connect');
			}
		});
	};
	
	$.executarAjax = function(options) {
		var defaults = {
			crossDomain : true,
			type : 'POST',
			dataType : 'json',
			data : $('form').serialize(),
			beforeSend : function(jqXHR) {
				jqXHR.overrideMimeType('text/html;charset=iso-8859-1');
				$.blockUI({
					message : 'Aguarde carregando...'
				});
			},
			error : function(XHR, textStatus, errorThrown) {
				console.log('erro');
				console.log('xhr', XHR);
				console.log(textStatus + ":" + errorThrown);
			},
			complete : function(response) {
				limparMensagens();
				$.unblockUI();
				var responseText = retorno = JSON.parse(response.responseText);
				if (responseText.actionErrors && responseText.actionErrors.length > 0) {
					erros(responseText.actionErrors);
				} else if (responseText.actionMessages && responseText.actionMessages.length > 0) {
					sucesso(responseText.actionMessages);
				}
				if (options.afterComplete) {
					options.afterComplete();
				}
			}
		};
		// Faz o merge das opções informadas com as defaults
		$.extend(defaults, options);
		// Executa a chamada ajax
		$.ajax(defaults);
	};
	
	function limparMensagens() {
		$('#myModalErros-body').html('');
		$('#myModalSucess-body').html('');
	}
	
	function erros(mensagens) {
		var errorMessage = '';
		$.each(mensagens, function(index, value) {
			errorMessage += value + '<br />';
		});
		$('#myModalErros-body').html(errorMessage);
	}
	
	function sucesso(mensagens) {
		var mensagensSucesso = '';
		$.each(mensagens, function(index, value) {
			mensagensSucesso += value + '<br />';
		});
		$('#myModalSucess-body').html(mensagensSucesso);
	}
	
});