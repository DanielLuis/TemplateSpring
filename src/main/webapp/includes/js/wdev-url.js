(function($){
	
	/*
		API para registrar URL's 
		
		Exemplo de uso atraves do console do browser:
			
			> window.baseURL="App-AppTeste";
			"App-AppTeste"
			
			> $.url.get("a", {x: 1}); 
			Obtendo url a  -> template 'undefined'
			Error: A url 'a' nao esta cadastrada
			
			> $.url.add("a", "oi/{x}");
			Adicionando a url 'a' => 'oi/{x}'
			undefined
			
			> $.url.get("a")
			Obtendo url 'a' -> template 'oi/{x}'
			"App-AppTeste/oi/{x}"
			
			> $.url.get("a", {x: 1})
			Obtendo url 'a'  -> template 'oi/{x}'
			Valor url 'a' -> � 'oi/1'
			"App-AppTeste/oi/1"

			> $.url.add({"a": "oia/{x}", "b": "oib/{x}"});
			Sobrescrevendo a url 'a' => 'oi/{x}' com o valor => 'oia/{x}'
			Adicionando a url 'b' => 'oib/{x}'
	*/
	$.url = {
		/**
		 * Obter uma URL cadastrada, substituindo parametros {} se for necess�rio.
		 * 
		 * Exemplo: $.url.get("dummy")	
		 *    retorno -> dummy/a/{a}/b/{b}/c/{c}/dummy
		 *
		 * Exemplo: $.url.get("dummy", {a:1, b:2});
		 *    retorno -> dummy/a/1/b/2/c/{c}/dummy
		 */
		get: function(key, params, config) {
			if (key == null) {
				throw new Error("A chave da url deve ser informada");
			}
			config = $.extend({open: "{", close:"}", baseURL: window.baseURL || "", concat: function (urlA, urlB) {
				urlA = urlA || "";
				urlB = urlB || "";
				urlA = $.trim(urlA);
				urlB = $.trim(urlB);
				return (urlA.charAt(urlA.length - 1) == "/" ? urlA : urlA + "/") + (urlB.charAt(0) == "/" ? urlB.substring(1) : urlB);
			}}, config);
			var url = $.url.table[key];
			if (url == null) {
				throw new Error("A url '" + key + "' nao est� cadastrada");
			}
			if (params == null) {
				return config.concat(config.baseURL, url);
			}
			$.each(params, function(key, value) {
				url = url.replace(config.open + key + config.close, value);				
			});
			return config.concat(config.baseURL, url);
		}
		/**
		 * Cadastra uma ou mais urls
		 */
		,add: function(key, url) {
			if ($.type(key) == 'string' && $.type(url) == 'string') {
				$.url.table[key] = url;
			} else if ($.type(key) == 'object') {
				$.each(key, function(urlKey, url) {
					$.url.table[urlKey] = url;	
				});
			}
		}
		/**
		 * Registro de urls do sistema
		 */
		,table: {
			"dummy" : "dummy/a/{a}/b/{b}/c/{c}/dummy"
		}
	};
})(jQuery);