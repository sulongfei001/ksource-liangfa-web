function clearAll() {
	var formId = '';
	var id;
	var args = clearAll.arguments;
	if (args.length == 0) {
		for ( var i = 0; i < document.forms.length; i++)
			formId = formId + i + ',';
	} else {
		formId = args[0] + ',';
	}
	while (formId.indexOf(',') > 0) {
		id = formId.substring(0, formId.indexOf(','));
		formId = formId.substring(formId.indexOf(',') + 1);
		for (i = 0; i < document.forms[id].elements.length; i++) {
			// alert(document.forms[id].elements[i].id+"--"+document.forms[id].elements[i].name+"--"+document.forms[id].elements[i].type
			// +"---"+document.forms[id].elements[i].value);

			if (document.forms[id].elements[i].type == 'text'
					&& document.forms[id].elements[i].disabled == false)
				document.forms[id].elements[i].value = '';
			else if (document.forms[id].elements[i].type == 'textarea'
					&& document.forms[id].elements[i].disabled == false)
				document.forms[id].elements[i].value = '';
			else if (document.forms[id].elements[i].type == 'select-one')
				document.forms[id].elements[i].value = document.forms[id].elements[i].options[0].value;
			else if (document.forms[id].elements[i].type == 'checkbox')
			    document.forms[id].elements[i].checked= '';
		}
	}
}
