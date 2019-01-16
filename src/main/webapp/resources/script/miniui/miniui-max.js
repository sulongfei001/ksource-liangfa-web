mini = {
	components: {},
	uids: {},
	ux: {},
	isReady: false,
	byClass: function(_, $) {
		if(typeof $ == "string") $ = Hcd($);
		return jQuery("." + _, $)[0]
	},
	getComponents: function() {
		var _ = [];
		for(var A in mini.components) {
			var $ = mini.components[A];
			_.push($)
		}
		return _
	},
	get: function(_) {
		if(!_) return null;
		if(mini.isControl(_)) return _;
		if(typeof _ == "string") if(_.charAt(0) == "#") _ = _.substr(1);
		if(typeof _ == "string") return mini.components[_];
		else {
			var $ = mini.uids[_.uid];
			if($ && $.el == _) return $
		}
		return null
	},
	getbyUID: function($) {
		return mini.uids[$]
	},
	findControls: function(E, B) {
		if(!E) return [];
		B = B || mini;
		var $ = [],
			D = mini.uids;
		for(var A in D) {
			var _ = D[A],
				C = E[GkN](B, _);
			if(C === true || C === 1) {
				$.push(_);
				if(C === 1) break
			}
		}
		return $
	},
	emptyFn: function() {},
	createNameControls: function(A, F) {
		if(!A || !A.el) return;
		if(!F) F = "_";
		var C = A.el,
			$ = mini.findControls(function($) {
				if(!$.el || !$.name) return false;
				if(TWAc(C, $.el)) return true;
				return false
			});
		for(var _ = 0, D = $.length; _ < D; _++) {
			var B = $[_],
				E = F + B.name;
			if(F === true) E = B.name[0].toUpperCase() + B.name.substring(1, B.name.length);
			A[E] = B
		}
	},
	getbyName: function(C, _) {
		var B = mini.isControl(_),
			A = _;
		if(_ && B) _ = _.el;
		_ = Hcd(_);
		_ = _ || document.body;
		var $ = this.findControls(function($) {
			if(!$.el) return false;
			if($.name == C && TWAc(_, $.el)) return 1;
			return false
		}, this);
		if(B && $.length == 0 && A && A.getbyName) return A.getbyName(C);
		return $[0]
	},
	getParams: function(C) {
		if(!C) C = location.href;
		C = C.split("?")[1];
		var B = {};
		if(C) {
			var A = C.split("&");
			for(var _ = 0, D = A.length; _ < D; _++) {
				var $ = A[_].split("=");
				B[$[0]] = decodeURIComponent($[1])
			}
		}
		return B
	},
	reg: function($) {
		this.components[$.id] = $;
		this.uids[$.uid] = $
	},
	unreg: function($) {
		delete mini.components[$.id];
		delete mini.uids[$.uid]
	},
	classes: {},
	uiClasses: {},
	getClass: function($) {
		if(!$) return null;
		return this.classes[$.toLowerCase()]
	},
	getClassByUICls: function($) {
		return this.uiClasses[$.toLowerCase()]
	},
	idPre: "mini-",
	idIndex: 1,
	newId: function($) {
		return($ || this.idPre) + this.idIndex++
	},
	copyTo: function($, A) {
		if($ && A) for(var _ in A) $[_] = A[_];
		return $
	},
	copyIf: function($, A) {
		if($ && A) for(var _ in A) if(mini.isNull($[_])) $[_] = A[_];
		return $
	},
	createDelegate: function(_, $) {
		if(!_) return function() {};
		return function() {
			return _.apply($, arguments)
		}
	},
	isControl: function($) {
		return !!($ && $.isControl)
	},
	isElement: function($) {
		return $ && $.appendChild
	},
	isDate: function($) {
		return $ && $.getFullYear
	},
	isArray: function($) {
		return $ && !! $.unshift
	},
	isNull: function($) {
		return $ === null || $ === undefined
	},
	isNumber: function($) {
		return typeof $ == "number"
	},
	isEquals: function($, _) {
		if($ !== 0 && _ !== 0) if((mini.isNull($) || $ == "") && (mini.isNull(_) || _ == "")) return true;
		if($ && _ && $.getFullYear && _.getFullYear) return $.getTime() === _.getTime();
		if(typeof $ == "object" && typeof _ == "object" && $ === _) return true;
		return String($) === String(_)
	},
	forEach: function(E, D, B) {
		var _ = E.clone();
		for(var A = 0, C = _.length; A < C; A++) {
			var $ = _[A];
			if(D[GkN](B, $, A, E) === false) break
		}
	},
	sort: function(A, _, $) {
		$ = $ || A;
		A.sort(_)
	},
	removeNode: function($) {
		jQuery($).remove()
	},
	elWarp: document.createElement("div")
};
PC7 = function(A, _) {
	_ = _.toLowerCase();
	if(!mini.classes[_]) {
		mini.classes[_] = A;
		A[LMj].type = _
	}
	var $ = A[LMj].uiCls;
	if(!mini.isNull($) && !mini.uiClasses[$]) mini.uiClasses[$] = A
};
Iov = function(E, A, $) {
	if(typeof A != "function") return this;
	var D = E,
		C = D.prototype,
		_ = A[LMj];
	if(D[GR_] == _) return;
	D[GR_] = _;
	D[GR_][KNT] = A;
	for(var B in _) C[B] = _[B];
	if($) for(B in $) C[B] = $[B];
	return D
};
mini.copyTo(mini, {
	extend: Iov,
	regClass: PC7,
	debug: false
});
VJ6 = [];
IZY2 = function(_, $) {
	VJ6.push([_, $]);
	if(!mini._EventTimer) mini._EventTimer = setTimeout(function() {
		AL()
	}, 1)
};
AL = function() {
	for(var $ = 0, _ = VJ6.length; $ < _; $++) {
		var A = VJ6[$];
		A[0][GkN](A[1])
	}
	VJ6 = [];
	mini._EventTimer = null
};
YMK = function(C) {
	if(typeof C != "string") return null;
	var _ = C.split("."),
		D = null;
	for(var $ = 0, A = _.length; $ < A; $++) {
		var B = _[$];
		if(!D) D = window[B];
		else D = D[B];
		if(!D) break
	}
	return D
};
mini.getAndCreate = function($) {
	if(!$) return null;
	if(typeof $ == "string") return mini.components[$];
	if(typeof $ == "object") if(mini.isControl($)) return $;
	else if(mini.isElement($)) return mini.uids[$.uid];
	else return mini.create($);
	return null
};
mini.create = function($) {
	if(!$) return null;
	if(mini.get($.id) === $) return $;
	var _ = this.getClass($.type);
	if(!_) return null;
	var A = new _();
	A.set($);
	return A
};
mini.append = function(_, A) {
	_ = Hcd(_);
	if(!A || !_) return;
	if(typeof A == "string") {
		if(A.charAt(0) == "#") {
			A = Hcd(A);
			if(!A) return;
			_.appendChild(A);
			return A
		} else {
			if(A.indexOf("<tr") == 0) {
				return jQuery(_).append(A)[0].lastChild;
				return
			}
			var $ = document.createElement("div");
			$.innerHTML = A;
			A = $.firstChild;
			while($.firstChild) _.appendChild($.firstChild);
			return A
		}
	} else {
		_.appendChild(A);
		return A
	}
};
mini.prepend = function(_, A) {
	if(typeof A == "string") if(A.charAt(0) == "#") A = Hcd(A);
	else {
		var $ = document.createElement("div");
		$.innerHTML = A;
		A = $.firstChild
	}
	return jQuery(_).prepend(A)[0].firstChild
};
var LzT = "getBottomVisibleColumns",
	PZ = "setFrozenStartColumn",
	CJP = "showCollapseButton",
	F0q = "showFolderCheckBox",
	ZNk = "setFrozenEndColumn",
	Qq1 = "getAncestorColumns",
	AOJr = "getFilterRowHeight",
	TL9 = "checkSelectOnLoad",
	LdGz = "frozenStartColumn",
	Q3Gi = "allowResizeColumn",
	Q9r = "showExpandButtons",
	Pjk9 = "requiredErrorText",
	Lq1n = "getMaxColumnLevel",
	Et = "isAncestorColumn",
	PSk = "allowAlternating",
	Mh5i = "getBottomColumns",
	_sF = "isShowRowDetail",
	SR = "allowCellSelect",
	Stg = "showAllCheckBox",
	T94 = "frozenEndColumn",
	J_9 = "allowMoveColumn",
	G1s = "allowSortColumn",
	XUW = "refreshOnExpand",
	$KS = "showCloseButton",
	Dr = "unFrozenColumns",
	U1O = "getParentColumn",
	H$ = "isVisibleColumn",
	XZ5f = "getFooterHeight",
	Ykh2 = "getHeaderHeight",
	Lb = "_createColumnId",
	JRy = "getRowDetailEl",
	JSYh = "scrollIntoView",
	R3e = "setColumnWidth",
	NanW = "setCurrentCell",
	R4v = "allowRowSelect",
	AMb = "showSummaryRow",
	Pv_b = "showVGridLines",
	SNM = "showHGridLines",
	PiyK = "checkRecursive",
	U9 = "enableHotTrack",
	OsZw = "popupMaxHeight",
	Jrd = "popupMinHeight",
	EPnR = "refreshOnClick",
	B0rI = "getColumnWidth",
	U_ = "getEditRowData",
	OZX = "getParentNode",
	GnY = "removeNodeCls",
	DGV = "showRowDetail",
	_1a = "hideRowDetail",
	FfrM = "commitEditRow",
	M3A1 = "beginEditCell",
	SjLH = "allowCellEdit",
	CkfO = "decimalPlaces",
	PLQ = "showFilterRow",
	IlH = "dropGroupName",
	ABB = "dragGroupName",
	Pi = "showTreeLines",
	VTHw = "popupMaxWidth",
	_0 = "popupMinWidth",
	Rhc = "showMinButton",
	XS = "showMaxButton",
	OIAh = "getChildNodes",
	UIk = "getCellEditor",
	LFM = "cancelEditRow",
	V2c = "getRowByValue",
	AuO = "removeItemCls",
	QZ8f = "_createCellId",
	CYs = "_createItemId",
	$J = "setValueField",
	GC1O = "getAncestors",
	XI8 = "collapseNode",
	T2cA = "removeRowCls",
	Y_m = "getColumnBox",
	HbI = "showCheckBox",
	PV = "autoCollapse",
	OvNg = "showTreeIcon",
	UmO = "checkOnClick",
	$o_ = "defaultValue",
	EeuC = "resultAsData",
	ATIK = "resultAsTree",
	ENl = "_ParseString",
	Ez9 = "getItemValue",
	ZPmn = "_createRowId",
	_H = "isAutoHeight",
	F6 = "findListener",
	$Tgc = "getRegionEl",
	ZcNC = "removeClass",
	VH1K = "isFirstNode",
	JFP = "getSelected",
	UXi = "setSelected",
	V0 = "multiSelect",
	_8K6 = "tabPosition",
	DzN = "columnWidth",
	Jgq = "handlerSize",
	MHU = "allowSelect",
	Dzv = "popupHeight",
	Wiwj = "contextMenu",
	Gl = "borderStyle",
	Rcs = "parentField",
	Xfv = "closeAction",
	Vl4 = "_rowIdField",
	JkX = "allowResize",
	GYYg = "showToolbar",
	KO2 = "deselectAll",
	S6d = "treeToArray",
	I2J = "eachColumns",
	Jach = "getItemText",
	J4j = "isAutoWidth",
	S1B = "_initEvents",
	KNT = "constructor",
	XiOJ = "addNodeCls",
	LaOj = "expandNode",
	Z9i = "setColumns",
	Etn = "cancelEdit",
	Y5 = "moveColumn",
	BnX = "removeNode",
	QGK = "setCurrent",
	Ak = "totalCount",
	Q_D = "popupWidth",
	D7b = "titleField",
	B$Gk = "valueField",
	BEZ = "showShadow",
	B8 = "showFooter",
	MR$ = "findParent",
	S56 = "_getColumn",
	XD9s = "_ParseBool",
	J9 = "clearEvent",
	OP4p = "getCellBox",
	OJs = "selectText",
	YKu = "setVisible",
	BX1N = "isGrouping",
	GY = "addItemCls",
	Jm = "isSelected",
	RBE = "isReadOnly",
	GR_ = "superclass",
	JN = "getRegion",
	REM = "isEditing",
	OsRe = "hidePopup",
	NP = "removeRow",
	GCXn = "addRowCls",
	NfR = "increment",
	TCq = "allowDrop",
	JAYp = "pageIndex",
	DP = "iconStyle",
	UFag = "errorMode",
	LOda = "textField",
	YSqP = "groupName",
	PN = "showEmpty",
	QGN = "emptyText",
	IoF = "showModal",
	ZUT$ = "getColumn",
	DTTy = "getHeight",
	GGt = "_ParseInt",
	Zpt = "showPopup",
	W01 = "updateRow",
	Q_AF = "deselects",
	WH4 = "isDisplay",
	P4 = "setHeight",
	NeI = "removeCls",
	LMj = "prototype",
	Mc = "addClass",
	B31 = "isEquals",
	PiF = "maxValue",
	Njfb = "minValue",
	Q$ = "showBody",
	V6d = "tabAlign",
	$F_V = "sizeList",
	FX_r = "pageSize",
	PP = "urlField",
	Egr = "readOnly",
	Y1Q = "getWidth",
	$hR = "isFrozen",
	M3b = "loadData",
	R4rm = "deselect",
	UD7 = "setValue",
	UzD = "validate",
	$gN = "getAttrs",
	_wJ = "setWidth",
	Mdk = "doUpdate",
	Fcv = "doLayout",
	F2e = "renderTo",
	Chg = "setText",
	OzAi = "idField",
	CyM = "getNode",
	GmG = "getItem",
	CX7 = "repaint",
	SsB = "selects",
	OUQ = "setData",
	Sir = "_create",
	EqU5 = "destroy",
	IZ = "jsName",
	Ykd = "getRow",
	M$ = "select",
	LU = "within",
	QlR = "addCls",
	Ivp = "render",
	Wib = "setXY",
	GkN = "call";
SgZ = function() {
	this.Oso = {};
	this.uid = mini.newId(this._1e);
	if(!this.id) this.id = this.uid;
	mini.reg(this)
};
SgZ[LMj] = {
	isControl: true,
	id: null,
	_1e: "mini-",
	OET: false,
	G3e$: true,
	set: function(B) {
		if(typeof B == "string") return this;
		var _ = this.ERW;
		this.ERW = false;
		var C = B[F2e] || B[Ivp];
		delete B[F2e];
		delete B[Ivp];
		for(var $ in B) if($.toLowerCase().indexOf("on") == 0) {
			var F = B[$];
			this.on($.substring(2, $.length).toLowerCase(), F);
			delete B[$]
		}
		for($ in B) {
			var E = B[$],
				D = "set" + $.charAt(0).toUpperCase() + $.substring(1, $.length),
				A = this[D];
			if(A) A[GkN](this, E);
			else this[$] = E
		}
		if(C && this[Ivp]) this[Ivp](C);
		this.ERW = _;
		if(this[Fcv]) this[Fcv]();
		return this
	},
	fire: function(A, B) {
		if(this.G3e$ == false) return;
		A = A.toLowerCase();
		var _ = this.Oso[A];
		if(_) {
			if(!B) B = {};
			if(B && B != this) {
				B.source = B.sender = this;
				if(!B.type) B.type = A
			}
			for(var $ = 0, D = _.length; $ < D; $++) {
				var C = _[$];
				if(C) C[0].apply(C[1], [B])
			}
		}
	},
	on: function(type, fn, scope) {
		if(typeof fn == "string") {
			var f = YMK(fn);
			if(!f) {
				var id = mini.newId("__str_");
				window[id] = fn;
				eval("fn = function(e){var s = " + id + ";var fn = YMK(s); if(fn) {fn[GkN](this,e)}else{eval(s);}}")
			} else fn = f
		}
		if(typeof fn != "function" || !type) return false;
		type = type.toLowerCase();
		var event = this.Oso[type];
		if(!event) event = this.Oso[type] = [];
		scope = scope || this;
		if(!this[F6](type, fn, scope)) event.push([fn, scope]);
		return this
	},
	un: function($, C, _) {
		if(typeof C != "function") return false;
		$ = $.toLowerCase();
		var A = this.Oso[$];
		if(A) {
			_ = _ || this;
			var B = this[F6]($, C, _);
			if(B) A.remove(B)
		}
		return this
	},
	findListener: function(A, E, B) {
		A = A.toLowerCase();
		B = B || this;
		var _ = this.Oso[A];
		if(_) for(var $ = 0, D = _.length; $ < D; $++) {
			var C = _[$];
			if(C[0] === E && C[1] === B) return C
		}
	},
	setId: function($) {
		if(!$) throw new Error("id not null");
		if(this.OET) throw new Error("id just set only one");
		mini["unreg"](this);
		this.id = $;
		if(this.el) this.el.id = $;
		if(this.IJ6) this.IJ6.id = $ + "$text";
		if(this.HRA) this.HRA.id = $ + "$value";
		this.OET = true;
		mini.reg(this)
	},
	getId: function() {
		return this.id
	},
	destroy: function() {
		mini["unreg"](this);
		this.fire("destroy")
	}
};
FIa = function() {
	FIa[GR_][KNT][GkN](this);
	this[Sir]();
	this.el.uid = this.uid;
	this[S1B]();
	if(this._clearBorder) this.el.style.borderWidth = "0";
	this[QlR](this.uiCls);
	this[_wJ](this.width);
	this[P4](this.height);
	this.el.style.display = this.visible ? this.C4U : "none"
};
Iov(FIa, SgZ, {
	jsName: null,
	width: "",
	height: "",
	visible: true,
	readOnly: false,
	enabled: true,
	tooltip: "",
	O1O: "mini-readonly",
	$Sj4: "mini-disabled",
	_create: function() {
		this.el = document.createElement("div")
	},
	_initEvents: function() {},
	within: function($) {
		if(TWAc(this.el, $.target)) return true;
		return false
	},
	name: "",
	setName: function($) {
		this.name = $
	},
	getName: function() {
		return this.name
	},
	isAutoHeight: function() {
		var $ = this.el.style.height;
		return $ == "auto" || $ == ""
	},
	isAutoWidth: function() {
		var $ = this.el.style.width;
		return $ == "auto" || $ == ""
	},
	isFixedSize: function() {
		var $ = this.width,
			_ = this.height;
		if(parseInt($) + "px" == $ && parseInt(_) + "px" == _) return true;
		return false
	},
	isRender: function($) {
		return !!(this.el && this.el.parentNode && this.el.parentNode.tagName)
	},
	render: function(_, $) {
		if(typeof _ === "string") if(_ == "#body") _ = document.body;
		else _ = Hcd(_);
		if(!_) return;
		if(!$) $ = "append";
		$ = $.toLowerCase();
		if($ == "before") jQuery(_).before(this.el);
		else if($ == "preend") jQuery(_).preend(this.el);
		else if($ == "after") jQuery(_).after(this.el);
		else _.appendChild(this.el);
		this.el.id = this.id;
		this[Fcv]();
		this.fire("render")
	},
	getEl: function() {
		return this.el
	},
	setJsName: function($) {
		this[IZ] = $;
		window[$] = this
	},
	getJsName: function() {
		return this[IZ]
	},
	setTooltip: function($) {
		this.tooltip = $;
		this.el.title = $
	},
	getTooltip: function() {
		return this.tooltip
	},
	_sizeChaned: function() {
		this[Fcv]()
	},
	setWidth: function($) {
		if(parseInt($) == $) $ += "px";
		this.width = $;
		this.el.style.width = $;
		this._sizeChaned()
	},
	getWidth: function(_) {
		var $ = _ ? jQuery(this.el).width() : jQuery(this.el).outerWidth();
		if(_ && this.TG) {
			var A = D0uu(this.TG);
			$ = $ - A.left - A.right
		}
		return $
	},
	setHeight: function($) {
		if(parseInt($) == $) $ += "px";
		this.height = $;
		this.el.style.height = $;
		this._sizeChaned()
	},
	getHeight: function(_) {
		var $ = _ ? jQuery(this.el).height() : jQuery(this.el).outerHeight();
		if(_ && this.TG) {
			var A = D0uu(this.TG);
			$ = $ - A.top - A.bottom
		}
		return $
	},
	getBox: function() {
		return Y3L(this.el)
	},
	setBorderStyle: function($) {
		var _ = this.TG || this.el;
		Gn(_, $);
		this[Fcv]()
	},
	getBorderStyle: function() {
		return this[Gl]
	},
	_clearBorder: true,
	setStyle: function($) {
		this.style = $;
		Gn(this.el, $);
		if(this._clearBorder) this.el.style.borderWidth = "0";
		this.width = this.el.style.width;
		this.height = this.el.style.height;
		this._sizeChaned()
	},
	getStyle: function() {
		return this.style
	},
	setCls: function($) {
		HMT(this.el, this.cls);
		V7(this.el, $);
		this.cls = $
	},
	getCls: function() {
		return this.cls
	},
	addCls: function($) {
		V7(this.el, $)
	},
	removeCls: function($) {
		HMT(this.el, $)
	},
	_doReadOnly: function() {
		if(this[Egr]) this[QlR](this.O1O);
		else this[NeI](this.O1O)
	},
	setReadOnly: function($) {
		this[Egr] = $;
		this._doReadOnly()
	},
	getReadOnly: function() {
		return this[Egr]
	},
	getParent: function(A) {
		var $ = document,
			B = this.el.parentNode;
		while(B != $ && B != null) {
			var _ = mini.get(B);
			if(_) {
				if(!mini.isControl(_)) return null;
				if(!A || _.uiCls == A) return _
			}
			B = B.parentNode
		}
		return null
	},
	isReadOnly: function() {
		if(this[Egr] || !this.enabled) return true;
		var $ = this.getParent();
		if($) return $[RBE]();
		return false
	},
	setEnabled: function($) {
		this.enabled = $;
		if(this.enabled) this[NeI](this.$Sj4);
		else this[QlR](this.$Sj4);
		this._doReadOnly()
	},
	getEnabled: function() {
		return this.enabled
	},
	enable: function() {
		this.setEnabled(true)
	},
	disable: function() {
		this.setEnabled(false)
	},
	C4U: "",
	setVisible: function($) {
		this.visible = $;
		if(this.el) {
			this.el.style.display = $ ? this.C4U : "none";
			this[Fcv]()
		}
	},
	getVisible: function() {
		return this.visible
	},
	show: function() {
		this[YKu](true)
	},
	hide: function() {
		this[YKu](false)
	},
	isDisplay: function() {
		if(Or0 == false) return false;
		var $ = document.body,
			_ = this.el;
		while(1) {
			if(_ == null || !_.style) return false;
			if(_ && _.style && _.style.display == "none") return false;
			if(_ == $) return true;
			_ = _.parentNode
		}
		return true
	},
	Y5o: true,
	beginUpdate: function() {
		this.Y5o = false
	},
	endUpdate: function() {
		this.Y5o = true;
		this[Mdk]()
	},
	doUpdate: function() {},
	canLayout: function() {
		if(this.ERW == false) return false;
		return this[WH4]()
	},
	doLayout: function() {},
	layoutChanged: function() {
		if(this.canLayout() == false) return;
		this[Fcv]()
	},
	destroy: function(_) {
		if(this.el);
		if(this.el) {
			mini[J9](this.el);
			if(_ !== false) {
				var $ = this.el.parentNode;
				if($) $.removeChild(this.el)
			}
		}
		this.TG = null;
		this.el = null;
		mini["unreg"](this);
		this.fire("destroy")
	},
	focus: function() {
		try {
			var $ = this;
			$.el.focus()
		} catch(_) {}
	},
	blur: function() {
		try {
			var $ = this;
			$.el.blur()
		} catch(_) {}
	},
	allowAnim: true,
	setAllowAnim: function($) {
		this.allowAnim = $
	},
	getAllowAnim: function() {
		return this.allowAnim
	},
	Qcra: function() {
		return this.el
	},
	mask: function($) {
		if(typeof $ == "string") $ = {
			html: $
		};
		$ = $ || {};
		$.el = this.Qcra();
		if(!$.cls) $.cls = this.$tJo;
		mini.mask($)
	},
	unmask: function() {
		mini.unmask(this.Qcra())
	},
	$tJo: "mini-mask-loading",
	loadingMsg: "Loading...",
	loading: function($) {
		this.mask($ || this.loadingMsg)
	},
	setLoadingMsg: function($) {
		this.loadingMsg = $
	},
	getLoadingMsg: function() {
		return this.loadingMsg
	},
	_getContextMenu: function($) {
		var _ = $;
		if(typeof $ == "string") {
			_ = mini.get($);
			if(!_) {
				mini.parse($);
				_ = mini.get($)
			}
		} else if(mini.isArray($)) _ = {
			type: "menu",
			items: $
		};
		else if(!mini.isControl($)) _ = mini.create($);
		return _
	},
	__OnHtmlContextMenu: function(_) {
		var $ = {
			popupEl: this.el,
			htmlEvent: _,
			cancel: false
		};
		this[Wiwj].fire("BeforeOpen", $);
		if($.cancel == true) return;
		this[Wiwj].fire("opening", $);
		if($.cancel == true) return;
		this[Wiwj].showAtPos(_.pageX, _.pageY);
		this[Wiwj].fire("Open", $);
		return false
	},
	contextMenu: null,
	setContextMenu: function($) {
		var _ = this._getContextMenu($);
		if(!_) return;
		if(this[Wiwj] !== _) {
			this[Wiwj] = _;
			this[Wiwj].owner = this;
			$v9(this.el, "contextmenu", this.__OnHtmlContextMenu, this)
		}
	},
	getContextMenu: function() {
		return this[Wiwj]
	},
	setDefaultValue: function($) {
		this[$o_] = $
	},
	getDefaultValue: function() {
		return this[$o_]
	},
	setValue: function($) {
		this.value = $
	},
	getValue: function() {
		return this.value
	},
	Nd: function($) {},
	getAttrs: function(C) {
		var I = {},
			F = C.className;
		if(F) I.cls = F;
		mini[ENl](C, I, ["id", "name", "width", "height", "borderStyle", "value", "defaultValue", "contextMenu", "tooltip"]);
		mini[XD9s](C, I, ["visible", "enabled", "readOnly"]);
		if(C[Egr]) I[Egr] = true;
		var E = C.style.cssText;
		if(E) I.style = E;
		if(isIE9) {
			var _ = C.style.background;
			if(_) {
				if(!I.style) I.style = "";
				I.style += ";background:" + _
			}
		}
		if(this.style) if(I.style) I.style = this.style + ";" + I.style;
		else I.style = this.style;
		if(this[Gl]) if(I[Gl]) I[Gl] = this[Gl] + ";" + I[Gl];
		else I[Gl] = this[Gl];
		var B = mini._attrs;
		if(B) for(var $ = 0, G = B.length; $ < G; $++) {
			var D = B[$],
				H = D[0],
				A = D[1];
			if(!A) A = "string";
			if(A == "string") mini[ENl](C, I, [H]);
			else if(A == "bool") mini[XD9s](C, I, [H]);
			else if(A == "int") mini[GGt](C, I, [H])
		}
		return I
	}
});
mini._attrs = null;
mini.regHtmlAttr = function(_, $) {
	if(!_) return;
	if(!$) $ = "string";
	if(!mini._attrs) mini._attrs = [];
	mini._attrs.push([_, $])
};
CLi = function() {
	CLi[GR_][KNT][GkN](this)
};
Iov(CLi, FIa, {
	required: false,
	requiredErrorText: "This field is required.",
	S2: "mini-required",
	errorText: "",
	EWXl: "mini-error",
	U8mQ: "mini-invalid",
	errorMode: "icon",
	validateOnChanged: true,
	Gla: true,
	validate: function() {
		var $ = {
			value: this.getValue(),
			errorText: "",
			isValid: true
		};
		if(this.required) if(mini.isNull($.value) || $.value === "") {
			$.isValid = false;
			$.errorText = this[Pjk9]
		}
		this.fire("validation", $);
		this.errorText = $.errorText;
		this.setIsValid($.isValid);
		return this.isValid()
	},
	isValid: function() {
		return this.Gla
	},
	setIsValid: function($) {
		this.Gla = $;
		this.OK$()
	},
	getIsValid: function() {
		return this.Gla
	},
	setValidateOnChanged: function($) {
		this.validateOnChanged = $
	},
	getValidateOnChanged: function($) {
		return this.validateOnChanged
	},
	setErrorMode: function($) {
		if(!$) $ = "none";
		this[UFag] = $.toLowerCase();
		if(this.Gla == false) this.OK$()
	},
	getErrorMode: function() {
		return this[UFag]
	},
	setErrorText: function($) {
		this.errorText = $;
		if(this.Gla == false) this.OK$()
	},
	getErrorText: function() {
		return this.errorText
	},
	setRequired: function($) {
		this.required = $;
		if(this.required) this[QlR](this.S2);
		else this[NeI](this.S2)
	},
	getRequired: function() {
		return this.required
	},
	setRequiredErrorText: function($) {
		this[Pjk9] = $
	},
	getRequiredErrorText: function() {
		return this[Pjk9]
	},
	errorIconEl: null,
	getErrorIconEl: function() {
		return this.Izgy
	},
	Kg: function() {},
	OK$: function() {
		var $ = this;
		setTimeout(function() {
			$.Lf_()
		}, 1)
	},
	Lf_: function() {
		this[NeI](this.EWXl);
		this[NeI](this.U8mQ);
		this.el.title = "";
		if(this.Gla == false) switch(this[UFag]) {
		case "icon":
			this[QlR](this.EWXl);
			var $ = this.getErrorIconEl();
			if($) $.title = this.errorText;
			break;
		case "border":
			this[QlR](this.U8mQ);
			this.el.title = this.errorText;
		default:
			this.Kg();
			break
		} else this.Kg();
		this[Fcv]()
	},
	PV2: function() {
		if(this.validateOnChanged) this[UzD]();
		this.fire("valuechanged", {
			value: this.getValue()
		})
	},
	onValueChanged: function(_, $) {
		this.on("valuechanged", _, $)
	},
	onValidation: function(_, $) {
		this.on("validation", _, $)
	},
	getAttrs: function(_) {
		var A = CLi[GR_][$gN][GkN](this, _);
		mini[ENl](_, A, ["onvaluechanged", "onvalidation", "requiredErrorText", "errorMode"]);
		mini[XD9s](_, A, ["validateOnChanged"]);
		var $ = _.getAttribute("required");
		if(!$) $ = _.required;
		if($) A.required = $ != "false" ? true : false;
		return A
	}
});
GR = function() {
	this.data = [];
	this.MyQ = [];
	GR[GR_][KNT][GkN](this);
	this[Mdk]()
};
Iov(GR, CLi, {
	defaultValue: "",
	value: "",
	valueField: "id",
	textField: "text",
	delimiter: ",",
	data: null,
	url: "",
	GM_: "mini-list-item",
	OSK: "mini-list-item-hover",
	_O2q: "mini-list-item-selected",
	set: function(A) {
		if(typeof A == "string") return this;
		var $ = A.value;
		delete A.value;
		var B = A.url;
		delete A.url;
		var _ = A.data;
		delete A.data;
		GR[GR_].set[GkN](this, A);
		if(!mini.isNull(_)) this[OUQ](_);
		if(!mini.isNull(B)) this.setUrl(B);
		if(!mini.isNull($)) this[UD7]($);
		return this
	},
	uiCls: "mini-list",
	_create: function() {},
	_initEvents: function() {
		IZY2(function() {
			MT(this.el, "click", this.PGY, this);
			MT(this.el, "dblclick", this.Esh, this);
			MT(this.el, "mousedown", this.AOlf, this);
			MT(this.el, "mouseup", this.Hfq, this);
			MT(this.el, "mousemove", this.VeS, this);
			MT(this.el, "mouseover", this.LZR, this);
			MT(this.el, "mouseout", this.$MHa, this);
			MT(this.el, "keydown", this.HtB, this);
			MT(this.el, "keyup", this.O83, this);
			MT(this.el, "contextmenu", this.M35, this)
		}, this)
	},
	destroy: function($) {
		if(this.el) {
			this.el.onclick = null;
			this.el.ondblclick = null;
			this.el.onmousedown = null;
			this.el.onmouseup = null;
			this.el.onmousemove = null;
			this.el.onmouseover = null;
			this.el.onmouseout = null;
			this.el.onkeydown = null;
			this.el.onkeyup = null;
			this.el.oncontextmenu = null
		}
		GR[GR_][EqU5][GkN](this, $)
	},
	name: "",
	setName: function($) {
		this.name = $;
		if(this.HRA) mini.setAttr(this.HRA, "name", this.name)
	},
	Tlpp: function(_) {
		var A = ZW(_.target, this.GM_);
		if(A) {
			var $ = parseInt(mini.getAttr(A, "index"));
			return this.data[$]
		}
	},
	addItemCls: function(_, A) {
		var $ = this.getItemEl(_);
		if($) V7($, A)
	},
	removeItemCls: function(_, A) {
		var $ = this.getItemEl(_);
		if($) HMT($, A)
	},
	getItemEl: function(_) {
		_ = this[GmG](_);
		var $ = this.data.indexOf(_),
			A = this.A_iN($);
		return document.getElementById(A)
	},
	Kro: function(_, $) {
		_ = this[GmG](_);
		if(!_) return;
		var A = this.getItemEl(_);
		if($ && A) this[JSYh](_);
		if(this._jItem == _) return;
		this.ZeW();
		this._jItem = _;
		V7(A, this.OSK)
	},
	ZeW: function() {
		if(!this._jItem) return;
		var $ = this.getItemEl(this._jItem);
		if($) HMT($, this.OSK);
		this._jItem = null
	},
	getFocusedItem: function() {
		return this._jItem
	},
	getFocusedIndex: function() {
		return this.data.indexOf(this._jItem)
	},
	XbE: null,
	scrollIntoView: function(_) {
		try {
			var $ = this.getItemEl(_),
				A = this.XbE || this.el;
			mini[JSYh]($, A, false)
		} catch(B) {}
	},
	getItem: function($) {
		if(typeof $ == "object") return $;
		if(typeof $ == "number") return this.data[$];
		return this.findItems($)[0]
	},
	getCount: function() {
		return this.data.length
	},
	indexOf: function($) {
		return this.data.indexOf($)
	},
	getAt: function($) {
		return this.data[$]
	},
	updateItem: function($, _) {
		$ = this[GmG]($);
		if(!$) return;
		mini.copyTo($, _);
		this[Mdk]()
	},
	load: function($) {
		if(typeof $ == "string") this.setUrl($);
		else this[OUQ]($)
	},
	loadData: function($) {
		this[OUQ]($)
	},
	setData: function(data) {
		if(typeof data == "string") data = eval(data);
		if(!mini.isArray(data)) data = [];
		this.data = data;
		this[Mdk]();
		var records = this.findItems(this.value);
		this[SsB](records)
	},
	getData: function() {
		return this.data.clone()
	},
	setUrl: function($) {
		this.url = $;
		this.YqFk({})
	},
	getUrl: function() {
		return this.url
	},
	YqFk: function(params) {
		try {
			this.url = eval(this.url)
		} catch(e) {}
		var e = {
			url: this.url,
			async: false,
			type: "get",
			params: params,
			cancel: false
		};
		this.fire("beforeload", e);
		if(e.cancel == true) return;
		var sf = this;
		this.HIU = jQuery.ajax({
			url: e.url,
			async: e.async,
			data: e.params,
			type: e.type,
			cache: false,
			dataType: "text",
			success: function($) {
				var _ = null;
				try {
					_ = mini.decode($)
				} catch(A) {}
				var A = {
					data: _,
					cancel: false
				};
				sf.fire("preload", A);
				if(A.cancel == true) return;
				sf[OUQ](A.data);
				sf.fire("load");
				setTimeout(function() {
					sf[Fcv]()
				}, 100)
			},
			error: function($, A, _) {
				var B = {
					xmlHttp: $,
					errorCode: A
				};
				sf.fire("loaderror", B)
			}
		})
	},
	setValue: function($) {
		if(mini.isNull($)) $ = "";
		if(this.value !== $) {
			var _ = this.findItems(this.value);
			this[Q_AF](_);
			this.value = $;
			if(this.HRA) this.HRA.value = $;
			_ = this.findItems(this.value);
			this[SsB](_)
		}
	},
	getValue: function() {
		return this.value
	},
	getFormValue: function() {
		return this.value
	},
	setValueField: function($) {
		this[B$Gk] = $
	},
	getValueField: function() {
		return this[B$Gk]
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	getItemValue: function($) {
		return String($[this.valueField])
	},
	getItemText: function($) {
		var _ = $[this.textField];
		return mini.isNull(_) ? "" : String(_)
	},
	B5fK: function(A) {
		if(mini.isNull(A)) A = [];
		if(!mini.isArray(A)) A = this.findItems(A);
		var B = [],
			C = [];
		for(var _ = 0, D = A.length; _ < D; _++) {
			var $ = A[_];
			if($) {
				B.push(this[Ez9]($));
				C.push(this[Jach]($))
			}
		}
		return [B.join(this.delimiter), C.join(this.delimiter)]
	},
	findItems: function(B) {
		if(mini.isNull(B) || B === "") return [];
		var E = String(B).split(this.delimiter),
			D = this.data,
			H = {};
		for(var F = 0, A = D.length; F < A; F++) {
			var _ = D[F],
				I = _[this.valueField];
			H[I] = _
		}
		var C = [];
		for(var $ = 0, G = E.length; $ < G; $++) {
			I = E[$], _ = H[I];
			if(_) C.push(_)
		}
		return C
	},
	YF: null,
	MyQ: [],
	multiSelect: false,
	B42d: function() {
		for(var _ = this.MyQ.length - 1; _ >= 0; _--) {
			var $ = this.MyQ[_];
			if(this.data.indexOf($) == -1) this.MyQ.removeAt(_)
		}
		var A = this.B5fK(this.MyQ);
		this.value = A[0];
		if(this.HRA) this.HRA.value = this.value
	},
	setMultiSelect: function($) {
		this[V0] = $
	},
	getMultiSelect: function() {
		return this[V0]
	},
	isSelected: function($) {
		if(!$) return false;
		return this.MyQ.indexOf($) != -1
	},
	getSelecteds: function() {
		return this.MyQ.clone()
	},
	setSelected: function($) {
		if($) {
			this.YF = $;
			this[M$]($)
		}
	},
	getSelected: function() {
		return this.YF
	},
	select: function($) {
		$ = this[GmG]($);
		if(!$) return;
		if(this[Jm]($)) return;
		this[SsB]([$])
	},
	deselect: function($) {
		$ = this[GmG]($);
		if(!$) return;
		if(!this[Jm]($)) return;
		this[Q_AF]([$])
	},
	selectAll: function() {
		var $ = this.data.clone();
		this[SsB]($)
	},
	deselectAll: function() {
		this[Q_AF](this.MyQ)
	},
	clearSelect: function() {
		this[KO2]()
	},
	selects: function(A) {
		if(!A || A.length == 0) return;
		A = A.clone();
		for(var _ = 0, B = A.length; _ < B; _++) {
			var $ = A[_];
			if(!this[Jm]($)) this.MyQ.push($)
		}
		this.Lbq()
	},
	deselects: function(A) {
		if(!A || A.length == 0) return;
		A = A.clone();
		for(var _ = A.length - 1; _ >= 0; _--) {
			var $ = A[_];
			if(this[Jm]($)) this.MyQ.remove($)
		}
		this.Lbq()
	},
	Lbq: function() {
		var C = this.B5fK(this.MyQ);
		this.value = C[0];
		if(this.HRA) this.HRA.value = this.value;
		for(var A = 0, D = this.data.length; A < D; A++) {
			var _ = this.data[A],
				F = this[Jm](_);
			if(F) this[GY](_, this._O2q);
			else this[AuO](_, this._O2q);
			var $ = this.data.indexOf(_),
				E = this.Sd($),
				B = document.getElementById(E);
			if(B) B.checked = !! F
		}
	},
	WeH: function(_, B) {
		var $ = this.B5fK(this.MyQ);
		this.value = $[0];
		if(this.HRA) this.HRA.value = this.value;
		var A = {
			selecteds: this.getSelecteds(),
			selected: this[JFP](),
			value: this.getValue()
		};
		this.fire("SelectionChanged", A)
	},
	Sd: function($) {
		return this.uid + "$ck$" + $
	},
	A_iN: function($) {
		return this.uid + "$" + $
	},
	PGY: function($) {
		this.E4yM($, "Click")
	},
	Esh: function($) {
		this.E4yM($, "Dblclick")
	},
	AOlf: function($) {
		this.E4yM($, "MouseDown")
	},
	Hfq: function($) {
		this.E4yM($, "MouseUp")
	},
	VeS: function($) {
		this.E4yM($, "MouseMove")
	},
	LZR: function($) {
		this.E4yM($, "MouseOver")
	},
	$MHa: function($) {
		this.E4yM($, "MouseOut")
	},
	HtB: function($) {
		this.E4yM($, "KeyDown")
	},
	O83: function($) {
		this.E4yM($, "KeyUp")
	},
	M35: function($) {
		this.E4yM($, "ContextMenu")
	},
	E4yM: function(C, A) {
		if(!this.enabled) return;
		var $ = this.Tlpp(C);
		if(!$) return;
		var B = this["_OnItem" + A];
		if(B) B[GkN](this, $, C);
		else {
			var _ = {
				item: $,
				htmlEvent: C
			};
			this.fire("item" + A, _)
		}
	},
	_OnItemClick: function($, A) {
		if(this[RBE]() || this.enabled == false || $.enabled === false) {
			A.preventDefault();
			return
		}
		var _ = this.getValue();
		if(this[V0]) {
			if(this[Jm]($)) {
				this[R4rm]($);
				if(this.YF == $) this.YF = null
			} else {
				this[M$]($);
				this.YF = $
			}
			this.WeH()
		} else if(!this[Jm]($)) {
			this[KO2]();
			this[M$]($);
			this.YF = $;
			this.WeH()
		}
		if(_ != this.getValue()) this.PV2();
		var A = {
			item: $,
			htmlEvent: A
		};
		this.fire("itemclick", A)
	},
	W7c: true,
	_OnItemMouseOut: function($, _) {
		if(!this.enabled) return;
		if(this.W7c) this.ZeW();
		var _ = {
			item: $,
			htmlEvent: _
		};
		this.fire("itemmouseout", _)
	},
	_OnItemMouseMove: function($, _) {
		if(!this.enabled || $.enabled === false) return;
		this.Kro($);
		var _ = {
			item: $,
			htmlEvent: _
		};
		this.fire("itemmousemove", _)
	},
	onItemClick: function(_, $) {
		this.on("itemclick", _, $)
	},
	onItemMouseDown: function(_, $) {
		this.on("itemmousedown", _, $)
	},
	onBeforeLoad: function(_, $) {
		this.on("beforeload", _, $)
	},
	onLoad: function(_, $) {
		this.on("load", _, $)
	},
	onLoadError: function(_, $) {
		this.on("loaderror", _, $)
	},
	onPreLoad: function(_, $) {
		this.on("preload", _, $)
	},
	getAttrs: function(C) {
		var G = GR[GR_][$gN][GkN](this, C);
		mini[ENl](C, G, ["url", "data", "value", "textField", "valueField", "onitemclick", "onitemmousemove", "onselectionchanged", "onitemdblclick", "onbeforeload", "onload", "onloaderror", "ondataload"]);
		mini[XD9s](C, G, ["multiSelect"]);
		var E = G[B$Gk] || this[B$Gk],
			B = G[LOda] || this[LOda];
		if(C.nodeName.toLowerCase() == "select") {
			var D = [];
			for(var A = 0, F = C.length; A < F; A++) {
				var _ = C.options[A],
					$ = {};
				$[B] = _.text;
				$[E] = _.value;
				D.push($)
			}
			if(D.length > 0) G.data = D
		}
		return G
	}
});
mini._Layouts = {};
mini.layout = function($, _) {
	function A(C) {
		var D = mini.get(C);
		if(D) {
			if(D[Fcv]) if(!mini._Layouts[D.uid]) {
				mini._Layouts[D.uid] = D;
				if(_ !== false || D.isFixedSize() == false) D[Fcv](false);
				delete mini._Layouts[D.uid]
			}
		} else {
			var E = C.childNodes;
			if(E) for(var $ = 0, F = E.length; $ < F; $++) {
				var B = E[$];
				A(B)
			}
		}
	}
	if(!$) $ = document.body;
	A($)
};
mini.applyTo = function(_) {
	_ = Hcd(_);
	if(!_) return this;
	if(mini.get(_)) throw new Error("not applyTo a mini control");
	var $ = this[$gN](_);
	delete $._applyTo;
	if(mini.isNull($[$o_]) && !mini.isNull($.value)) $[$o_] = $.value;
	var A = _.parentNode;
	if(A && this.el != _) A.replaceChild(this.el, _);
	this.set($);
	this.Nd(_);
	return this
};
mini._doParse = function(G) {
	var F = G.nodeName.toLowerCase();
	if(!F) return;
	var B = G.className;
	if(B) {
		var $ = mini.get(G);
		if(!$) {
			var H = B.split(" ");
			for(var E = 0, C = H.length; E < C; E++) {
				var A = H[E],
					I = mini.getClassByUICls(A);
				if(I) {
					var D = new I();
					mini.applyTo[GkN](D, G);
					G = D.el;
					break
				}
			}
		}
	}
	if(F == "select" || XPZ(G, "mini-menu") || XPZ(G, "mini-datagrid") || XPZ(G, "mini-treegrid") || XPZ(G, "mini-tree") || XPZ(G, "mini-button") || XPZ(G, "mini-textbox") || XPZ(G, "mini-buttonedit")) return;
	var J = mini[OIAh](G, true);
	for(E = 0, C = J.length; E < C; E++) {
		var _ = J[E];
		if(_.nodeType == 1) if(_.parentNode == G) mini._doParse(_)
	}
};
mini._Removes = [];
mini.parse = function($) {
	if(typeof $ == "string") {
		var A = $;
		$ = Hcd(A);
		if(!$) $ = document.body
	}
	if($ && !mini.isElement($)) $ = $.el;
	if(!$) $ = document.body;
	var _ = Or0;
	if(isIE) Or0 = false;
	mini._doParse($);
	Or0 = _;
	mini.layout($)
};
mini[ENl] = function(B, A, E) {
	for(var $ = 0, D = E.length; $ < D; $++) {
		var C = E[$],
			_ = mini.getAttr(B, C);
		if(_) A[C] = _
	}
};
mini[XD9s] = function(B, A, E) {
	for(var $ = 0, D = E.length; $ < D; $++) {
		var C = E[$],
			_ = mini.getAttr(B, C);
		if(_) A[C] = _ == "true" ? true : false
	}
};
mini[GGt] = function(B, A, E) {
	for(var $ = 0, D = E.length; $ < D; $++) {
		var C = E[$],
			_ = parseInt(mini.getAttr(B, C));
		if(!isNaN(_)) A[C] = _
	}
};
mini._ParseColumns = function(N) {
	var G = [],
		O = mini[OIAh](N);
	for(var M = 0, H = O.length; M < H; M++) {
		var C = O[M],
			T = jQuery(C),
			D = {},
			J = null,
			K = null,
			_ = mini[OIAh](C);
		if(_) for(var $ = 0, P = _.length; $ < P; $++) {
			var B = _[$],
				A = jQuery(B).attr("property");
			if(!A) continue;
			A = A.toLowerCase();
			if(A == "columns") {
				D.columns = mini._ParseColumns(B);
				jQuery(B).remove()
			}
			if(A == "editor" || A == "filter") {
				var F = B.className,
					R = F.split(" ");
				for(var L = 0, S = R.length; L < S; L++) {
					var E = R[L],
						Q = mini.getClassByUICls(E);
					if(Q) {
						var I = new Q();
						if(A == "filter") {
							K = I[$gN](B);
							K.type = I.type
						} else {
							J = I[$gN](B);
							J.type = I.type
						}
						break
					}
				}
				jQuery(B).remove()
			}
		}
		D.header = C.innerHTML;
		mini[ENl](C, D, ["name", "header", "field", "editor", "filter", "renderer", "width", "type", "renderer", "headerAlign", "align", "headerCls", "cellCls", "headerStyle", "cellStyle", "displayField", "dateFormat", "listFormat", "mapFormat", "trueValue", "falseValue"]);
		mini[XD9s](C, D, ["visible", "readOnly", "allowSort", "allowReisze", "allowMove", "allowDrag", "autoShowPopup"]);
		if(J) D.editor = J;
		if(K) D.filter = K;
		G.push(D)
	}
	return G
};
mini._Columns = {};
mini[S56] = function($) {
	var _ = mini._Columns[$.toLowerCase()];
	if(!_) return {};
	return _()
};
mini.IndexColumn = function($) {
	return mini.copyTo({
		width: 30,
		cellCls: "",
		align: "center",
		draggable: false,
		init: function($) {
			$.on("addrow", this.__OnIndexChanged, this);
			$.on("removerow", this.__OnIndexChanged, this);
			$.on("moverow", this.__OnIndexChanged, this);
			if($.isTree) {
				$.on("loadnode", this.__OnIndexChanged, this);
				this._gridUID = $.uid;
				this[Vl4] = "_id"
			}
		},
		getNumberId: function($) {
			return this._gridUID + "$number$" + $[this._rowIdField]
		},
		createNumber: function($, _) {
			if(mini.isNull($[JAYp])) return _ + 1;
			else return($[JAYp] * $[FX_r]) + _ + 1
		},
		renderer: function(A) {
			var $ = A.sender;
			if(this.draggable) {
				if(!A.cellStyle) A.cellStyle = "";
				A.cellStyle += ";cursor:move;"
			}
			var _ = "<div id=\"" + this.getNumberId(A.record) + "\">";
			if(mini.isNull($[JAYp])) _ += A.rowIndex + 1;
			else _ += ($[JAYp] * $[FX_r]) + A.rowIndex + 1;
			_ += "</div>";
			return _
		},
		__OnIndexChanged: function(F) {
			var $ = F.sender,
				C = $.toArray();
			for(var A = 0, D = C.length; A < D; A++) {
				var _ = C[A],
					E = this.getNumberId(_),
					B = document.getElementById(E);
				if(B) B.innerHTML = this.createNumber($, A)
			}
		}
	}, $)
};
mini._Columns["indexcolumn"] = mini.IndexColumn;
mini.CheckColumn = function($) {
	return mini.copyTo({
		width: 30,
		cellCls: "mini-checkcolumn",
		headerCls: "mini-checkcolumn",
		_multiRowSelect: true,
		header: function($) {
			var A = this.uid + "checkall",
				_ = "<input type=\"checkbox\" id=\"" + A + "\" />";
			if(this[V0] == false) _ = "";
			return _
		},
		getCheckId: function($) {
			return this._gridUID + "$checkcolumn$" + $[this._rowIdField]
		},
		init: function($) {
			$.on("selectionchanged", this.QeY, this);
			$.on("HeaderCellClick", this.ZPgb, this)
		},
		renderer: function(C) {
			var B = this.getCheckId(C.record),
				_ = C.sender[Jm](C.record),
				A = "checkbox",
				$ = C.sender;
			if($[V0] == false) A = "radio";
			return "<input type=\"" + A + "\" id=\"" + B + "\" " + (_ ? "checked" : "") + " hidefocus style=\"outline:none;\" onclick=\"return false\"/>"
		},
		ZPgb: function(B) {
			var $ = B.sender,
				A = $.uid + "checkall",
				_ = document.getElementById(A);
			if(_) if($[V0]) {
				if(_.checked) $.selectAll();
				else $[KO2]()
			} else {
				$[KO2]();
				if(_.checked) $[M$](0)
			}
		},
		QeY: function(G) {
			var $ = G.sender,
				C = $.toArray();
			for(var A = 0, D = C.length; A < D; A++) {
				var _ = C[A],
					F = $[Jm](_),
					E = $.uid + "$checkcolumn$" + _[$._rowIdField],
					B = document.getElementById(E);
				if(B) B.checked = F
			}
		}
	}, $)
};
mini._Columns["checkcolumn"] = mini.CheckColumn;
mini.ExpandColumn = function($) {
	return mini.copyTo({
		width: 30,
		cellCls: "",
		align: "center",
		draggable: false,
		cellStyle: "padding:0",
		renderer: function($) {
			return "<a class=\"mini-grid-ecIcon\" href=\"javascript:#\" onclick=\"return false\"></a>"
		},
		init: function($) {
			$.on("cellclick", this.WBz, this)
		},
		WBz: function(A) {
			var $ = A.sender;
			if(A.column == this && $[_sF]) if(ZW(A.htmlEvent.target, "mini-grid-ecIcon")) {
				var _ = $[_sF](A.record);
				if($.autoHideRowDetail) $.hideAllRowDetail();
				if(_) $[_1a](A.record);
				else $[DGV](A.record)
			}
		}
	}, $)
};
mini._Columns["expandcolumn"] = mini.ExpandColumn;
OgFColumn = function($) {
	return mini.copyTo({
		header: "#",
		headerAlign: "center",
		cellCls: "mini-checkcolumn",
		trueValue: true,
		falseValue: false,
		readOnly: false,
		getCheckId: function($) {
			return this._gridUID + "$checkbox$" + $._id
		},
		renderer: function(B) {
			var A = this.getCheckId(B.record),
				_ = B.record[B.field] == this.trueValue ? true : false,
				$ = "checkbox";
			return "<input type=\"" + $ + "\" id=\"" + A + "\" " + (_ ? "checked" : "") + " hidefocus style=\"outline:none;\" onclick=\"return false;\"/>"
		},
		init: function($) {
			this.grid = $;
			$.on("cellclick", function(C) {
				if(C.column == this) {
					if(this[Egr]) return;
					var B = this.getCheckId(C.record),
						A = C.htmlEvent.target;
					if(A.id == B) {
						C.cancel = false;
						C.value = C.record[C.field];
						$.fire("cellbeginedit", C);
						if(C.cancel !== true) {
							var _ = C.record[C.field] == this.trueValue ? this.falseValue : this.trueValue;
							if($.QV) $.QV(C.record, C.column, _)
						}
					}
				}
			}, this);
			var _ = parseInt(this.trueValue),
				A = parseInt(this.falseValue);
			if(!isNaN(_)) this.trueValue = _;
			if(!isNaN(A)) this.falseValue = A
		}
	}, $)
};
mini._Columns["checkboxcolumn"] = OgFColumn;
ZjGColumn = function($) {
	return mini.copyTo({
		renderer: function(M) {
			var _ = M.value ? String(M.value) : "",
				C = _.split(","),
				D = "id",
				J = "text",
				A = {},
				G = M.column.editor;
			if(G && G.type == "combobox") {
				var B = this._combobox;
				if(!B) {
					if(mini.isControl(G)) B = G;
					else B = mini.create(G);
					this._combobox = B
				}
				D = B.getValueField();
				J = B.getTextField();
				A = this._valueMaps;
				if(!A) {
					A = {};
					var K = B.getData();
					for(var H = 0, E = K.length; H < E; H++) {
						var $ = K[H];
						A[$[D]] = $
					}
					this._valueMaps = A
				}
			}
			var L = [];
			for(H = 0, E = C.length; H < E; H++) {
				var F = C[H],
					$ = A[F];
				if($) {
					var I = $[J] || "";
					L.push(I)
				}
			}
			return L.join(",")
		}
	}, $)
};
mini._Columns["comboboxcolumn"] = ZjGColumn;
Ahk = function($) {
	this.owner = $;
	$v9(this.owner.el, "mousedown", this.AOlf, this)
};
Ahk[LMj] = {
	AOlf: function(_) {
		if(XPZ(_.target, "mini-grid-resizeGrid") && this.owner[JkX]) {
			var $ = this.KCmB();
			$.start(_)
		}
	},
	KCmB: function() {
		if(!this._resizeDragger) this._resizeDragger = new mini.Drag({
			capture: true,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this._resizeDragger
	},
	Jb: function($) {
		this.proxy = mini.append(document.body, "<div class=\"mini-grid-resizeProxy\"></div>");
		this.proxy.style.cursor = "se-resize";
		this.elBox = Y3L(this.owner.el);
		$vA(this.proxy, this.elBox)
	},
	Pc4: function(B) {
		var $ = this.owner,
			D = B.now[0] - B.init[0],
			_ = B.now[1] - B.init[1],
			A = this.elBox.width + D,
			C = this.elBox.height + _;
		if(A < $.minWidth) A = $.minWidth;
		if(C < $.minHeight) C = $.minHeight;
		if(A > $.maxWidth) A = $.maxWidth;
		if(C > $.maxHeight) C = $.maxHeight;
		mini.setSize(this.proxy, A, C)
	},
	IzO: function($, A) {
		if(!this.proxy) return;
		var _ = Y3L(this.proxy);
		jQuery(this.proxy).remove();
		this.proxy = null;
		this.elBox = null;
		if(A) {
			this.owner[_wJ](_.width);
			this.owner[P4](_.height)
		}
	}
};
mini.__IFrameCreateCount = 1;
mini.createIFrame = function(D, C, G) {
	var F = "__iframe_onload" + mini.__IFrameCreateCount++;
	window[F] = _;
	var E = "<iframe src=\"" + D + "\" style=\"width:100%;height:100%;\" onload=\"" + F + "()\" frameborder=\"0\"></iframe>",
		$ = document.createElement("div"),
		B = mini.append($, E),
		A = true;

	function _() {
		setTimeout(function() {
			if(C) C(B, A);
			A = false
		}, 1)
	}
	B._ondestroy = function() {
		if(G) G(B);
		window[F] = mini.emptyFn;
		B.src = "";
		B._ondestroy = null;
		B = null
	};
	return B
};
MFeq = function(C) {
	if(typeof C == "string") C = {
		url: C
	};
	C = mini.copyTo({
		width: 700,
		height: 400,
		allowResize: true,
		allowModal: true,
		title: "",
		titleIcon: "",
		iconCls: "",
		iconStyle: "",
		bodyStyle: "padding:0",
		url: "",
		showCloseButton: true,
		showFooter: false
	}, C);
	C[Xfv] = "destroy";
	var $ = C.onload;
	delete C.onload;
	var B = C.ondestroy;
	delete C.ondestroy;
	var _ = C.url;
	delete C.url;
	var A = new EzY();
	A.set(C);
	A.load(_, $, B);
	A.show();
	return A
};
mini.open = function(B) {
	if(!B) return;
	B.Owner = window;
	var $ = [];

	function _(A) {
		if(A.mini) $.push(A);
		if(A.parent && A.parent != A) _(A.parent)
	}
	_(window);
	var A = $[$.length - 1];
	return A.MFeq(B)
};
mini.openTop = mini.open;
mini.getData = function(C, A, E, D, _) {
	var $ = mini.getText(C, A, E, D, _),
		B = mini.decode($);
	return B
};
mini.getText = function(B, A, D, C, _) {
	var $ = null;
	jQuery.ajax({
		url: B,
		data: A,
		async: false,
		type: _ ? _ : "get",
		cache: false,
		dataType: "text",
		success: function(A, _) {
			$ = A
		},
		error: C
	});
	return $
};
if(!window.mini_RootPath) mini_RootPath = "/";
AY6 = function(B) {
	var A = document.getElementsByTagName("script"),
		D = "";
	for(var $ = 0, E = A.length; $ < E; $++) {
		var C = A[$].src;
		if(C.indexOf(B) != -1) {
			var F = C.split(B);
			D = F[0];
			break
		}
	}
	var _ = location.href;
	_ = _.split("#")[0];
	_ = _.split("?")[0];
	F = _.split("/");
	F.length = F.length - 1;
	_ = F.join("/");
	if(D.indexOf("http:") == -1 && D.indexOf("file:") == -1) D = _ + "/" + D;
	return D
};
if(!window.mini_JSPath) mini_JSPath = AY6("miniui.js");
mini.update = function(A, _) {
	if(typeof A == "string") A = {
		url: A
	};
	if(_) A.el = _;
	A = mini.copyTo({
		el: null,
		url: "",
		async: false,
		type: "get",
		cache: false,
		dataType: "text",
		success: function(_) {
			var B = A.el;
			if(B) {
				$(B).html(_);
				mini.parse(B)
			}
		},
		error: function($, A, _) {}
	}, A);
	jQuery.ajax(A)
};
mini.createSingle = function($) {
	if(typeof $ == "string") $ = mini.getClass($);
	if(typeof $ != "function") return;
	var _ = $.single;
	if(!_) _ = $.single = new $();
	return _
};
mini.createTopSingle = function($) {
	if(typeof $ != "function") return;
	var _ = $[LMj].type;
	if(top && top != window && top.mini && top.mini.getClass(_)) return top.mini.createSingle(_);
	else return mini.createSingle($)
};
mini.emptyFn = function() {};
mini.Drag = function($) {
	mini.copyTo(this, $)
};
mini.Drag[LMj] = {
	onStart: mini.emptyFn,
	onMove: mini.emptyFn,
	onStop: mini.emptyFn,
	capture: false,
	fps: 20,
	event: null,
	delay: 80,
	start: function(_) {
		_.preventDefault();
		if(_) this.event = _;
		this.now = this.init = [this.event.pageX, this.event.pageY];
		var $ = document;
		$v9($, "mousemove", this.move, this);
		$v9($, "mouseup", this.stop, this);
		$v9($, "contextmenu", this.contextmenu, this);
		if(this.context) $v9(this.context, "contextmenu", this.contextmenu, this);
		this.trigger = _.target;
		mini.selectable(this.trigger, false);
		mini.selectable($.body, false);
		if(this.capture) if(isIE) this.trigger.setCapture(true);
		else if(document.captureEvents) document.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP | Event.MOUSEDOWN);
		this.started = false;
		this.startTime = new Date()
	},
	contextmenu: function($) {
		if(this.context) M4(this.context, "contextmenu", this.contextmenu, this);
		M4(document, "contextmenu", this.contextmenu, this);
		$.preventDefault();
		$.stopPropagation()
	},
	move: function(_) {
		if(this.delay) if(new Date() - this.startTime < this.delay) return;
		if(!this.started) {
			this.started = true;
			this.onStart(this)
		}
		var $ = this;
		if(!this.timer) {
			$.now = [_.pageX, _.pageY];
			$.event = _;
			$.onMove($);
			$.timer = null
		}
	},
	stop: function(B) {
		this.now = [B.pageX, B.pageY];
		this.event = B;
		if(this.timer) {
			clearTimeout(this.timer);
			this.timer = null
		}
		var A = document;
		mini.selectable(this.trigger, true);
		mini.selectable(A.body, true);
		if(this.capture) if(isIE) this.trigger.releaseCapture();
		else if(document.captureEvents) document.releaseEvents(Event.MOUSEMOVE | Event.MOUSEUP | Event.MOUSEDOWN);
		var _ = mini.MouseButton.Right != B.button;
		if(_ == false) B.preventDefault();
		M4(A, "mousemove", this.move, this);
		M4(A, "mouseup", this.stop, this);
		var $ = this;
		setTimeout(function() {
			M4(document, "contextmenu", $.contextmenu, $);
			if($.context) M4($.context, "contextmenu", $.contextmenu, $)
		}, 1);
		if(this.started) this.onStop(this, _)
	}
};
mini.JSON = new(function() {
	var sb = [],
		useHasOwn = !! {}.hasOwnProperty,
		replaceString = function($, A) {
			var _ = m[A];
			if(_) return _;
			_ = A.charCodeAt();
			return "\\u00" + Math.floor(_ / 16).toString(16) + (_ % 16).toString(16)
		},
		doEncode = function($) {
			if($ === null) {
				sb[sb.length] = "null";
				return
			}
			var A = typeof $;
			if(A == "undefined") {
				sb[sb.length] = "null";
				return
			} else if($.push) {
				sb[sb.length] = "[";
				var D, _, C = $.length,
					E;
				for(_ = 0; _ < C; _ += 1) {
					E = $[_];
					A = typeof E;
					if(A == "undefined" || A == "function" || A == "unknown");
					else {
						if(D) sb[sb.length] = ",";
						doEncode(E);
						D = true
					}
				}
				sb[sb.length] = "]";
				return
			} else if($.getFullYear) {
				var B;
				sb[sb.length] = "\"";
				sb[sb.length] = $.getFullYear();
				sb[sb.length] = "-";
				B = $.getMonth() + 1;
				sb[sb.length] = B < 10 ? "0" + B : B;
				sb[sb.length] = "-";
				B = $.getDate();
				sb[sb.length] = B < 10 ? "0" + B : B;
				sb[sb.length] = "T";
				B = $.getHours();
				sb[sb.length] = B < 10 ? "0" + B : B;
				sb[sb.length] = ":";
				B = $.getMinutes();
				sb[sb.length] = B < 10 ? "0" + B : B;
				sb[sb.length] = ":";
				B = $.getSeconds();
				sb[sb.length] = B < 10 ? "0" + B : B;
				sb[sb.length] = "\"";
				return
			} else if(A == "string") {
				if(strReg1.test($)) {
					sb[sb.length] = "\"";
					sb[sb.length] = $.replace(strReg2, replaceString);
					sb[sb.length] = "\"";
					return
				}
				sb[sb.length] = "\"" + $ + "\"";
				return
			} else if(A == "number") {
				sb[sb.length] = $;
				return
			} else if(A == "boolean") {
				sb[sb.length] = String($);
				return
			} else {
				sb[sb.length] = "{";
				D, _, E;
				for(_ in $) if(!useHasOwn || $.hasOwnProperty(_)) {
					E = $[_];
					A = typeof E;
					if(A == "undefined" || A == "function" || A == "unknown");
					else {
						if(D) sb[sb.length] = ",";
						doEncode(_);
						sb[sb.length] = ":";
						doEncode(E);
						D = true
					}
				}
				sb[sb.length] = "}";
				return
			}
		},
		m = {
			"\b": "\\b",
			"\t": "\\t",
			"\n": "\\n",
			"\f": "\\f",
			"\r": "\\r",
			"\"": "\\\"",
			"\\": "\\\\"
		},
		strReg1 = /["\\\x00-\x1f]/,
		strReg2 = /([\x00-\x1f\\"])/g;
	this.encode = function() {
		var $;
		return function($, _) {
			sb = [];
			doEncode($);
			return sb.join("")
		}
	}();
	this.decode = function() {
		var re = /[\"\'](\d{4})-(\d{2})-(\d{2})[T ](\d{2}):(\d{2}):(\d{2})[\"\']/g;
		return function(json) {
			if(json === "" || json === null || json === undefined) return json;
			json = json.replace(re, "new Date($1,$2-1,$3,$4,$5,$6)");
			var s = eval("(" + json + ")");
			return s
		}
	}()
})();
mini.encode = mini.JSON.encode;
mini.decode = mini.JSON.decode;
mini.clone = function($) {
	if($ === null || $ === undefined) return $;
	var B = mini.encode($),
		_ = mini.decode(B);

	function A(B) {
		for(var _ = 0, D = B.length; _ < D; _++) {
			var $ = B[_];
			delete $._state;
			delete $._id;
			delete $._pid;
			for(var C in $) {
				var E = $[C];
				if(E instanceof Array) A(E)
			}
		}
	}
	A(_ instanceof Array ? _ : [_]);
	return _
};
var DAY_MS = 86400000,
	HOUR_MS = 3600000,
	MINUTE_MS = 60000;
mini.copyTo(mini, {
	clearTime: function($) {
		if(!$) return null;
		return new Date($.getFullYear(), $.getMonth(), $.getDate())
	},
	maxTime: function($) {
		if(!$) return null;
		return new Date($.getFullYear(), $.getMonth(), $.getDate(), 23, 59, 59)
	},
	cloneDate: function($) {
		if(!$) return null;
		return new Date($.getTime())
	},
	addDate: function(A, $, _) {
		if(!_) _ = "D";
		A = new Date(A.getTime());
		switch(_.toUpperCase()) {
		case "Y":
			A.setFullYear(A.getFullYear() + $);
			break;
		case "MO":
			A.setMonth(A.getMonth() + $);
			break;
		case "D":
			A.setDate(A.getDate() + $);
			break;
		case "H":
			A.setHours(A.getHours() + $);
			break;
		case "M":
			A.setMinutes(A.getMinutes() + $);
			break;
		case "S":
			A.setSeconds(A.getSeconds() + $);
			break;
		case "MS":
			A.setMilliseconds(A.getMilliseconds() + $);
			break
		}
		return A
	},
	getWeek: function(D, $, _) {
		$ += 1;
		var E = Math.floor((14 - ($)) / 12),
			G = D + 4800 - E,
			A = ($) + (12 * E) - 3,
			C = _ + Math.floor(((153 * A) + 2) / 5) + (365 * G) + Math.floor(G / 4) - Math.floor(G / 100) + Math.floor(G / 400) - 32045,
			F = (C + 31741 - (C % 7)) % 146097 % 36524 % 1461,
			H = Math.floor(F / 1460),
			B = ((F - H) % 365) + H;
		NumberOfWeek = Math.floor(B / 7) + 1;
		return NumberOfWeek
	},
	getWeekStartDate: function(C, B) {
		if(!B) B = 0;
		if(B > 6 || B < 0) throw new Error("out of weekday");
		var A = C.getDay(),
			_ = B - A;
		if(A < B) _ -= 7;
		var $ = new Date(C.getFullYear(), C.getMonth(), C.getDate() + _);
		return $
	},
	getShortWeek: function(_) {
		var $ = this.dateInfo.daysShort;
		return $[_]
	},
	getLongWeek: function(_) {
		var $ = this.dateInfo.daysLong;
		return $[_]
	},
	getShortMonth: function($) {
		var _ = this.dateInfo.monthsShort;
		return _[$]
	},
	getLongMonth: function($) {
		var _ = this.dateInfo.monthsLong;
		return _[$]
	},
	dateInfo: {
		monthsLong: ["January", "Febraury", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
		monthsShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
		daysLong: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
		daysShort: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
		quarterLong: ["Q1", "Q2", "Q3", "Q4"],
		quarterShort: ["Q1", "Q2", "Q3", "Q4"],
		halfYearLong: ["first half", "second half"],
		patterns: {
			"d": "M/d/yyyy",
			"D": "dddd,MMMM dd,yyyy",
			"f": "dddd,MMMM dd,yyyy H:mm tt",
			"F": "dddd,MMMM dd,yyyy H:mm:ss tt",
			"g": "M/d/yyyy H:mm tt",
			"G": "M/d/yyyy H:mm:ss tt",
			"m": "MMMM dd",
			"o": "yyyy-MM-ddTHH:mm:ss.fff",
			"s": "yyyy-MM-ddTHH:mm:ss",
			"t": "H:mm tt",
			"T": "H:mm:ss tt",
			"U": "dddd,MMMM dd,yyyy HH:mm:ss tt",
			"y": "MMM,yyyy"
		},
		tt: {
			"AM": "AM",
			"PM": "PM"
		},
		ten: {
			"Early": "Early",
			"Mid": "Mid",
			"Late": "Late"
		},
		today: "Today",
		clockType: 24
	}
});
Date[LMj].getHalfYear = function() {
	if(!this.getMonth) return null;
	var $ = this.getMonth();
	if($ < 6) return 0;
	return 1
};
Date[LMj].getQuarter = function() {
	if(!this.getMonth) return null;
	var $ = this.getMonth();
	if($ < 3) return 0;
	if($ < 6) return 1;
	if($ < 9) return 2;
	return 3
};
mini.formatDate = function(C, O, F) {
	if(!C || !C.getFullYear || isNaN(C)) return "";
	var G = C.toString(),
		B = mini.dateInfo;
	if(!B) B = mini.dateInfo;
	if(typeof(B) !== "undefined") {
		var M = typeof(B.patterns[O]) !== "undefined" ? B.patterns[O] : O,
			J = C.getFullYear(),
			$ = C.getMonth(),
			_ = C.getDate();
		if(O == "yyyy-MM-dd") {
			$ = $ + 1 < 10 ? "0" + ($ + 1) : $ + 1;
			_ = _ < 10 ? "0" + _ : _;
			return J + "-" + $ + "-" + _
		}
		if(O == "MM/dd/yyyy") {
			$ = $ + 1 < 10 ? "0" + ($ + 1) : $ + 1;
			_ = _ < 10 ? "0" + _ : _;
			return $ + "/" + _ + "/" + J
		}
		G = M.replace(/yyyy/g, J);
		G = G.replace(/yy/g, (J + "").substring(2));
		var L = C.getHalfYear();
		G = G.replace(/hy/g, B.halfYearLong[L]);
		var I = C.getQuarter();
		G = G.replace(/Q/g, B.quarterLong[I]);
		G = G.replace(/q/g, B.quarterShort[I]);
		G = G.replace(/MMMM/g, B.monthsLong[$].escapeDateTimeTokens());
		G = G.replace(/MMM/g, B.monthsShort[$].escapeDateTimeTokens());
		G = G.replace(/MM/g, $ + 1 < 10 ? "0" + ($ + 1) : $ + 1);
		G = G.replace(/(\\)?M/g, function(A, _) {
			return _ ? A : $ + 1
		});
		var N = C.getDay();
		G = G.replace(/dddd/g, B.daysLong[N].escapeDateTimeTokens());
		G = G.replace(/ddd/g, B.daysShort[N].escapeDateTimeTokens());
		G = G.replace(/dd/g, _ < 10 ? "0" + _ : _);
		G = G.replace(/(\\)?d/g, function(A, $) {
			return $ ? A : _
		});
		var H = C.getHours(),
			A = H > 12 ? H - 12 : H;
		if(B.clockType == 12) if(H > 12) H -= 12;
		G = G.replace(/HH/g, H < 10 ? "0" + H : H);
		G = G.replace(/(\\)?H/g, function(_, $) {
			return $ ? _ : H
		});
		G = G.replace(/hh/g, A < 10 ? "0" + A : A);
		G = G.replace(/(\\)?h/g, function(_, $) {
			return $ ? _ : A
		});
		var D = C.getMinutes();
		G = G.replace(/mm/g, D < 10 ? "0" + D : D);
		G = G.replace(/(\\)?m/g, function(_, $) {
			return $ ? _ : D
		});
		var K = C.getSeconds();
		G = G.replace(/ss/g, K < 10 ? "0" + K : K);
		G = G.replace(/(\\)?s/g, function(_, $) {
			return $ ? _ : K
		});
		G = G.replace(/fff/g, C.getMilliseconds());
		G = G.replace(/tt/g, C.getHours() > 12 || C.getHours() == 0 ? B.tt["PM"] : B.tt["AM"]);
		var C = C.getDate(),
			E = "";
		if(C <= 10) E = B.ten["Early"];
		else if(C <= 20) E = B.ten["Mid"];
		else E = B.ten["Late"];
		G = G.replace(/ten/g, E)
	}
	return G.replace(/\\/g, "")
};
String[LMj].escapeDateTimeTokens = function() {
	return this.replace(/([dMyHmsft])/g, "\\$1")
};
mini.fixDate = function($, _) {
	if(+$) while($.getDate() != _.getDate()) $.setTime(+$ + ($ < _ ? 1 : -1) * HOUR_MS)
};
mini.parseDate = function(A, _) {
	if(typeof A == "object") return isNaN(A) ? null : A;
	if(typeof A == "number") {
		var $ = new Date(A * 1000);
		if($.getTime() != A) return null;
		return isNaN($) ? null : $
	}
	if(typeof A == "string") {
		if(A.match(/^\d+(\.\d+)?$/)) {
			$ = new Date(parseFloat(A) * 1000);
			if($.getTime() != A) return null;
			else return $
		}
		if(_ === undefined) _ = true;
		$ = mini.parseISO8601(A, _) || (A ? new Date(A) : null);
		return isNaN($) ? null : $
	}
	return null
};
mini.parseISO8601 = function(D, $) {
	var _ = D.match(/^([0-9]{4})([-\/]([0-9]{1,2})([-\/]([0-9]{1,2})([T ]([0-9]{1,2}):([0-9]{1,2})(:([0-9]{1,2})(\.([0-9]+))?)?(Z|(([-+])([0-9]{2})(:?([0-9]{2}))?))?)?)?)?$/);
	if(!_) {
		_ = D.match(/^([0-9]{4})[-\/]([0-9]{2})[-\/]([0-9]{2})[T ]([0-9]{1,2})/);
		if(_) {
			var A = new Date(_[1], _[2] - 1, _[3], _[4]);
			return A
		}
		_ = D.match(/^([0-9]{2})-([0-9]{2})-([0-9]{4})$/);
		if(!_) return null;
		else {
			A = new Date(_[3], _[1] - 1, _[2]);
			return A
		}
	}
	A = new Date(_[1], 0, 1);
	if($ || !_[14]) {
		var C = new Date(_[1], 0, 1, 9, 0);
		if(_[3]) {
			A.setMonth(_[3] - 1);
			C.setMonth(_[3] - 1)
		}
		if(_[5]) {
			A.setDate(_[5]);
			C.setDate(_[5])
		}
		mini.fixDate(A, C);
		if(_[7]) A.setHours(_[7]);
		if(_[8]) A.setMinutes(_[8]);
		if(_[10]) A.setSeconds(_[10]);
		if(_[12]) A.setMilliseconds(Number("0." + _[12]) * 1000);
		mini.fixDate(A, C)
	} else {
		A.setUTCFullYear(_[1], _[3] ? _[3] - 1 : 0, _[5] || 1);
		A.setUTCHours(_[7] || 0, _[8] || 0, _[10] || 0, _[12] ? Number("0." + _[12]) * 1000 : 0);
		var B = Number(_[16]) * 60 + (_[18] ? Number(_[18]) : 0);
		B *= _[15] == "-" ? 1 : -1;
		A = new Date(+A + (B * 60 * 1000))
	}
	return A
};
mini.parseTime = function(E, F) {
	if(!E) return null;
	var B = parseInt(E);
	if(B == E && F) {
		$ = new Date(0);
		if(F[0] == "H") $.setHours(B);
		else if(F[0] == "m") $.setMinutes(B);
		else if(F[0] == "s") $.setSeconds(B);
		return $
	}
	var $ = mini.parseDate(E);
	if(!$) {
		var D = E.split(":"),
			_ = parseInt(D[0]),
			C = parseInt(D[1]),
			A = parseInt(D[2]);
		if(!isNaN(_) && !isNaN(C) && !isNaN(A)) {
			$ = new Date(0);
			$.setHours(_);
			$.setMinutes(C);
			$.setSeconds(A)
		}
		if(!isNaN(_) && (F == "H" || F == "HH")) {
			$ = new Date(0);
			$.setHours(_)
		} else if(!isNaN(_) && !isNaN(C) && (F == "H:mm" || F == "HH:mm")) {
			$ = new Date(0);
			$.setHours(_);
			$.setMinutes(C)
		} else if(!isNaN(_) && !isNaN(C) && F == "mm:ss") {
			$ = new Date(0);
			$.setMinutes(_);
			$.setSeconds(C)
		}
	}
	return $
};
mini.dateInfo = {
	monthsLong: ["\u4e00\u6708", "\u4e8c\u6708", "\u4e09\u6708", "\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708", "\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708", "\u5341\u4e00\u6708", "\u5341\u4e8c\u6708"],
	monthsShort: ["1\u6708", "2\u6708", "3\u6708", "4\u6708", "5\u6708", "6\u6708", "7\u6708", "8\u6708", "9\u6708", "10\u6708", "11\u6708", "12\u6708"],
	daysLong: ["\u661f\u671f\u65e5", "\u661f\u671f\u4e00", "\u661f\u671f\u4e8c", "\u661f\u671f\u4e09", "\u661f\u671f\u56db", "\u661f\u671f\u4e94", "\u661f\u671f\u516d"],
	daysShort: ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"],
	quarterLong: ["\u4e00\u5b63\u5ea6", "\u4e8c\u5b63\u5ea6", "\u4e09\u5b63\u5ea6", "\u56db\u5b63\u5ea6"],
	quarterShort: ["Q1", "Q2", "Q2", "Q4"],
	halfYearLong: ["\u4e0a\u534a\u5e74", "\u4e0b\u534a\u5e74"],
	patterns: {
		"d": "yyyy-M-d",
		"D": "yyyy\u5e74M\u6708d\u65e5",
		"f": "yyyy\u5e74M\u6708d\u65e5 H:mm",
		"F": "yyyy\u5e74M\u6708d\u65e5 H:mm:ss",
		"g": "yyyy-M-d H:mm",
		"G": "yyyy-M-d H:mm:ss",
		"m": "MMMd\u65e5",
		"o": "yyyy-MM-ddTHH:mm:ss.fff",
		"s": "yyyy-MM-ddTHH:mm:ss",
		"t": "H:mm",
		"T": "H:mm:ss",
		"U": "yyyy\u5e74M\u6708d\u65e5 HH:mm:ss",
		"y": "yyyy\u5e74MM\u6708"
	},
	tt: {
		"AM": "\u4e0a\u5348",
		"PM": "\u4e0b\u5348"
	},
	ten: {
		"Early": "\u4e0a\u65ec",
		"Mid": "\u4e2d\u65ec",
		"Late": "\u4e0b\u65ec"
	},
	today: "\u4eca\u5929",
	clockType: 24
};
Hcd = function($) {
	if(typeof $ == "string") {
		if($.charAt(0) == "#") $ = $.substr(1);
		return document.getElementById($)
	} else return $
};
XPZ = function($, _) {
	$ = Hcd($);
	if(!$) return;
	if(!$.className) return;
	var A = $.className.split(" ");
	return A.indexOf(_) != -1
};
V7 = function($, _) {
	if(!_) return;
	if(XPZ($, _) == false) jQuery($)[Mc](_)
};
HMT = function($, _) {
	if(!_) return;
	jQuery($)[ZcNC](_)
};
TA$ = function($) {
	$ = Hcd($);
	var _ = jQuery($);
	return {
		top: parseInt(_.css("margin-top"), 10) || 0,
		left: parseInt(_.css("margin-left"), 10) || 0,
		bottom: parseInt(_.css("margin-bottom"), 10) || 0,
		right: parseInt(_.css("margin-right"), 10) || 0
	}
};
D0uu = function($) {
	$ = Hcd($);
	var _ = jQuery($);
	return {
		top: parseInt(_.css("border-top-width"), 10) || 0,
		left: parseInt(_.css("border-left-width"), 10) || 0,
		bottom: parseInt(_.css("border-bottom-width"), 10) || 0,
		right: parseInt(_.css("border-right-width"), 10) || 0
	}
};
VO = function($) {
	$ = Hcd($);
	var _ = jQuery($);
	return {
		top: parseInt(_.css("padding-top"), 10) || 0,
		left: parseInt(_.css("padding-left"), 10) || 0,
		bottom: parseInt(_.css("padding-bottom"), 10) || 0,
		right: parseInt(_.css("padding-right"), 10) || 0
	}
};
Sbkj = function(_, $) {
	_ = Hcd(_);
	$ = parseInt($);
	if(isNaN($) || !_) return;
	if(jQuery.boxModel) {
		var A = VO(_),
			B = D0uu(_);
		$ = $ - A.left - A.right - B.left - B.right
	}
	if($ < 0) $ = 0;
	_.style.width = $ + "px"
};
SmU = function(_, $) {
	_ = Hcd(_);
	$ = parseInt($);
	if(isNaN($) || !_) return;
	if(jQuery.boxModel) {
		var A = VO(_),
			B = D0uu(_);
		$ = $ - A.top - A.bottom - B.top - B.bottom
	}
	if($ < 0) $ = 0;
	_.style.height = $ + "px"
};
R0 = function($, _) {
	$ = Hcd($);
	if($.style.display == "none" || $.type == "text/javascript") return 0;
	return _ ? jQuery($).width() : jQuery($).outerWidth()
};
KwY = function($, _) {
	$ = Hcd($);
	if($.style.display == "none" || $.type == "text/javascript") return 0;
	return _ ? jQuery($).height() : jQuery($).outerHeight()
};
$vA = function(A, C, B, $, _) {
	if(B === undefined) {
		B = C.y;
		$ = C.width;
		_ = C.height;
		C = C.x
	}
	mini[Wib](A, C, B);
	Sbkj(A, $);
	SmU(A, _)
};
Y3L = function(A) {
	var $ = mini.getXY(A),
		_ = {
			x: $[0],
			y: $[1],
			width: R0(A),
			height: KwY(A)
		};
	_.left = _.x;
	_.top = _.y;
	_.right = _.x + _.width;
	_.bottom = _.y + _.height;
	return _
};
Gn = function(A, B) {
	A = Hcd(A);
	if(!A || typeof B != "string") return;
	var F = jQuery(A),
		_ = B.toLowerCase().split(";");
	for(var $ = 0, C = _.length; $ < C; $++) {
		var E = _[$],
			D = E.split(":");
		if(D.length == 2) F.css(D[0].trim(), D[1].trim())
	}
};
FrU = function() {
	var $ = document.defaultView;
	return new Function("el", "style", ["style.indexOf('-')>-1 && (style=style.replace(/-(\\w)/g,function(m,a){return a.toUpperCase()}));", "style=='float' && (style='", $ ? "cssFloat" : "styleFloat", "');return el.style[style] || ", $ ? "window.getComputedStyle(el,null)[style]" : "el.currentStyle[style]", " || null;"].join(""))
}();
TWAc = function(A, $) {
	var _ = false;
	A = Hcd(A);
	$ = Hcd($);
	if(A === $) return true;
	if(A && $) if(A.contains) {
		try {
			return A.contains($)
		} catch(B) {
			return false
		}
	} else if(A.compareDocumentPosition) return !!(A.compareDocumentPosition($) & 16);
	else while($ = $.parentNode) _ = $ == A || _;
	return _
};
ZW = function(B, A, $) {
	B = Hcd(B);
	var C = document.body,
		_ = 0,
		D;
	$ = $ || 50;
	if(typeof $ != "number") {
		D = Hcd($);
		$ = 10
	}
	while(B && B.nodeType == 1 && _ < $ && B != C && B != D) {
		if(XPZ(B, A)) return B;
		_++;
		B = B.parentNode
	}
	return null
};
mini.copyTo(mini, {
	byId: Hcd,
	hasClass: XPZ,
	addClass: V7,
	removeClass: HMT,
	getMargins: TA$,
	getBorders: D0uu,
	getPaddings: VO,
	setWidth: Sbkj,
	setHeight: SmU,
	getWidth: R0,
	getHeight: KwY,
	setBox: $vA,
	getBox: Y3L,
	setStyle: Gn,
	getStyle: FrU,
	repaint: function($) {
		if(!$) $ = document.body;
		V7($, "mini-repaint");
		setTimeout(function() {
			HMT($, "mini-repaint")
		}, 1)
	},
	getSize: function($, _) {
		return {
			width: R0($, _),
			height: KwY($, _)
		}
	},
	setSize: function(A, $, _) {
		Sbkj(A, $);
		SmU(A, _)
	},
	setX: function(_, B) {
		var $ = jQuery(_).offset(),
			A = $.top;
		if(A === undefined) A = $[1];
		mini[Wib](_, B, A)
	},
	setY: function(_, A) {
		var $ = jQuery(_).offset(),
			B = $.left;
		if(B === undefined) B = $[0];
		mini[Wib](_, B, A)
	},
	setXY: function(_, B, A) {
		var $ = {
			left: B,
			top: A
		};
		jQuery(_).offset($);
		jQuery(_).offset($)
	},
	getXY: function(_) {
		var $ = jQuery(_).offset();
		return [$.left, $.top]
	},
	getViewportBox: function() {
		var $ = jQuery(window).width(),
			_ = jQuery(window).height(),
			B = jQuery(document).scrollLeft(),
			A = jQuery(document.body).scrollTop();
		if(document.documentElement) A = document.documentElement.scrollTop;
		return {
			x: B,
			y: A,
			width: $,
			height: _,
			right: B + $,
			bottom: A + _
		}
	},
	getChildNodes: function(A, C) {
		A = Hcd(A);
		if(!A) return;
		var E = A.childNodes,
			B = [];
		for(var $ = 0, D = E.length; $ < D; $++) {
			var _ = E[$];
			if(_.nodeType == 1 || C === true) B.push(_)
		}
		return B
	},
	removeChilds: function(B, _) {
		B = Hcd(B);
		if(!B) return;
		var C = mini[OIAh](B, true);
		for(var $ = 0, D = C.length; $ < D; $++) {
			var A = C[$];
			if(_ && A == _);
			else B.removeChild(C[$])
		}
	},
	isAncestor: TWAc,
	findParent: ZW,
	findChild: function(_, A) {
		_ = Hcd(_);
		var B = _.getElementsByTagName("*");
		for(var $ = 0, C = B.length; $ < C; $++) {
			var _ = B[$];
			if(XPZ(_, A)) return _
		}
	},
	isAncestor: function(A, $) {
		var _ = false;
		A = Hcd(A);
		$ = Hcd($);
		if(A === $) return true;
		if(A && $) if(A.contains) {
			try {
				return A.contains($)
			} catch(B) {
				return false
			}
		} else if(A.compareDocumentPosition) return !!(A.compareDocumentPosition($) & 16);
		else while($ = $.parentNode) _ = $ == A || _;
		return _
	},
	getOffsetsTo: function(_, A) {
		var $ = this.getXY(_),
			B = this.getXY(A);
		return [$[0] - B[0], $[1] - B[1]]
	},
	scrollIntoView: function(I, H, F) {
		var B = Hcd(H) || document.body,
			$ = this.getOffsetsTo(I, B),
			C = $[0] + B.scrollLeft,
			J = $[1] + B.scrollTop,
			D = J + I.offsetHeight,
			A = C + I.offsetWidth,
			G = B.clientHeight,
			K = parseInt(B.scrollTop, 10),
			_ = parseInt(B.scrollLeft, 10),
			L = K + G,
			E = _ + B.clientWidth;
		if(I.offsetHeight > G || J < K) B.scrollTop = J;
		else if(D > L) B.scrollTop = D - G;
		B.scrollTop = B.scrollTop;
		if(F !== false) {
			if(I.offsetWidth > B.clientWidth || C < _) B.scrollLeft = C;
			else if(A > E) B.scrollLeft = A - B.clientWidth;
			B.scrollLeft = B.scrollLeft
		}
		return this
	},
	setOpacity: function(_, $) {
		jQuery(_).css({
			"opacity": $
		})
	},
	selectable: function(_, $) {
		_ = Hcd(_);
		if( !! $) {
			jQuery(_)[ZcNC]("mini-unselectable");
			if(isIE) _.unselectable = "off";
			else {
				_.style.MozUserSelect = "";
				_.style.KhtmlUserSelect = "";
				_.style.UserSelect = ""
			}
		} else {
			jQuery(_)[Mc]("mini-unselectable");
			if(isIE) _.unselectable = "on";
			else {
				_.style.MozUserSelect = "none";
				_.style.UserSelect = "none";
				_.style.KhtmlUserSelect = "none"
			}
		}
	},
	selectRange: function(B, A, _) {
		if(B.createTextRange) {
			var $ = B.createTextRange();
			$.moveStart("character", A);
			$.moveEnd("character", _ - B.value.length);
			$[M$]()
		} else if(B.setSelectionRange) B.setSelectionRange(A, _);
		try {
			B.focus()
		} catch(C) {}
	},
	getSelectRange: function(A) {
		A = Hcd(A);
		if(!A) return;
		try {
			A.focus()
		} catch(C) {}
		var $ = 0,
			B = 0;
		if(A.createTextRange) {
			var _ = document.selection.createRange().duplicate();
			_.moveEnd("character", A.value.length);
			if(_.text === "") $ = A.value.length;
			else $ = A.value.lastIndexOf(_.text);
			_ = document.selection.createRange().duplicate();
			_.moveStart("character", -A.value.length);
			B = _.text.length
		} else {
			$ = A.selectionStart;
			B = A.selectionEnd
		}
		return [$, B]
	}
});
(function() {
	var $ = {
		tabindex: "tabIndex",
		readonly: "readOnly",
		"for": "htmlFor",
		"class": "className",
		maxlength: "maxLength",
		cellspacing: "cellSpacing",
		cellpadding: "cellPadding",
		rowspan: "rowSpan",
		colspan: "colSpan",
		usemap: "useMap",
		frameborder: "frameBorder",
		contenteditable: "contentEditable"
	},
		_ = document.createElement("div");
	_.setAttribute("class", "t");
	var A = _.className === "t";
	mini.setAttr = function(B, C, _) {
		B.setAttribute(A ? C : ($[C] || C), _)
	};
	mini.getAttr = function(B, C) {
		if(C == "value" && (isIE6 || isIE7)) {
			var _ = B.attributes[C];
			return _ ? _.value : null
		}
		var D = B.getAttribute(A ? C : ($[C] || C));
		if(typeof D == "function") D = B.attributes[C].value;
		return D
	}
})();
MT = function(_, $, C, A) {
	var B = "on" + $.toLowerCase();
	_[B] = function(_) {
		_ = _ || window.event;
		_.target = _.target || _.srcElement;
		var $ = C[GkN](A, _);
		if($ === false) return false
	}
};
$v9 = function(_, $, D, A) {
	_ = Hcd(_);
	A = A || _;
	if(!_ || !$ || !D || !A) return false;
	var B = mini[F6](_, $, D, A);
	if(B) return false;
	var C = mini.createDelegate(D, A);
	mini.listeners.push([_, $, D, A, C]);
	if(jQuery.browser.mozilla && $ == "mousewheel") $ = "DOMMouseScroll";
	jQuery(_).bind($, C)
};
M4 = function(_, $, C, A) {
	_ = Hcd(_);
	A = A || _;
	if(!_ || !$ || !C || !A) return false;
	var B = mini[F6](_, $, C, A);
	if(!B) return false;
	mini.listeners.remove(B);
	if(jQuery.browser.mozilla && $ == "mousewheel") $ = "DOMMouseScroll";
	jQuery(_).unbind($, B[4])
};
mini.copyTo(mini, {
	listeners: [],
	on: $v9,
	un: M4,
	findListener: function(A, _, F, B) {
		A = Hcd(A);
		B = B || A;
		if(!A || !_ || !F || !B) return false;
		var D = mini.listeners;
		for(var $ = 0, E = D.length; $ < E; $++) {
			var C = D[$];
			if(C[0] == A && C[1] == _ && C[2] == F && C[3] == B) return C
		}
	},
	clearEvent: function(A, _) {
		A = Hcd(A);
		if(!A) return false;
		var C = mini.listeners;
		for(var $ = C.length - 1; $ >= 0; $--) {
			var B = C[$];
			if(B[0] == A) if(!_ || _ == B[1]) M4(A, B[1], B[2], B[3])
		}
	}
});
mini.__windowResizes = [];
mini.onWindowResize = function(_, $) {
	mini.__windowResizes.push([_, $])
};
$v9(window, "resize", function(C) {
	var _ = mini.__windowResizes;
	for(var $ = 0, B = _.length; $ < B; $++) {
		var A = _[$];
		A[0][GkN](A[1], C)
	}
});
mini.copyTo(Array.prototype, {
	add: Array[LMj].enqueue = function($) {
		this[this.length] = $;
		return this
	},
	getRange: function(_, A) {
		var B = [];
		for(var $ = _; $ <= A; $++) B[B.length] = this[$];
		return B
	},
	addRange: function(A) {
		for(var $ = 0, _ = A.length; $ < _; $++) this[this.length] = A[$];
		return this
	},
	clear: function() {
		this.length = 0;
		return this
	},
	clone: function() {
		if(this.length === 1) return [this[0]];
		else return Array.apply(null, this)
	},
	contains: function($) {
		return(this.indexOf($) >= 0)
	},
	indexOf: function(_, B) {
		var $ = this.length;
		for(var A = (B < 0) ? Math.max(0, $ + B) : B || 0; A < $; A++) if(this[A] === _) return A;
		return -1
	},
	dequeue: function() {
		return this.shift()
	},
	insert: function(_, $) {
		this.splice(_, 0, $);
		return this
	},
	insertRange: function(_, B) {
		for(var A = B.length - 1; A >= 0; A--) {
			var $ = B[A];
			this.splice(_, 0, $)
		}
		return this
	},
	remove: function(_) {
		var $ = this.indexOf(_);
		if($ >= 0) this.splice($, 1);
		return($ >= 0)
	},
	removeAt: function($) {
		var _ = this[$];
		this.splice($, 1);
		return _
	},
	removeRange: function(_) {
		_ = _.clone();
		for(var $ = 0, A = _.length; $ < A; $++) this.remove(_[$])
	}
});
mini.Keyboard = {
	Left: 37,
	Top: 38,
	Right: 39,
	Bottom: 40,
	PageUp: 33,
	PageDown: 34,
	End: 35,
	Home: 36,
	Enter: 13,
	ESC: 27,
	Space: 32,
	Tab: 9,
	Del: 46,
	F1: 112,
	F2: 113,
	F3: 114,
	F4: 115,
	F5: 116,
	F6: 117,
	F7: 118,
	F8: 119,
	F9: 120,
	F10: 121,
	F11: 122,
	F12: 123
};
var ua = navigator.userAgent.toLowerCase(),
	check = function($) {
		return $.test(ua)
	},
	DOC = document,
	isStrict = DOC.compatMode == "CSS1Compat",
	isOpera = Object[LMj].toString[GkN](window.opera) == "[object Opera]",
	isChrome = check(/chrome/),
	isWebKit = check(/webkit/),
	isSafari = !isChrome && check(/safari/),
	isSafari2 = isSafari && check(/applewebkit\/4/),
	isSafari3 = isSafari && check(/version\/3/),
	isSafari4 = isSafari && check(/version\/4/),
	isIE = !! window.attachEvent && !isOpera,
	isIE7 = isIE && check(/msie 7/),
	isIE8 = isIE && check(/msie 8/),
	isIE9 = isIE && check(/msie 9/),
	isIE10 = isIE && document.documentMode == 10,
	isIE6 = isIE && !isIE7 && !isIE8 && !isIE9 && !isIE10,
	isFirefox = navigator.userAgent.indexOf("Firefox") > 0,
	isGecko = !isWebKit && check(/gecko/),
	isGecko2 = isGecko && check(/rv:1\.8/),
	isGecko3 = isGecko && check(/rv:1\.9/),
	isBorderBox = isIE && !isStrict,
	isWindows = check(/windows|win32/),
	isMac = check(/macintosh|mac os x/),
	isAir = check(/adobeair/),
	isLinux = check(/linux/),
	isSecure = /^https/i.test(window.location.protocol);
if(isIE6) {
	try {
		DOC.execCommand("BackgroundImageCache", false, true)
	} catch(e) {}
}
mini.isIE = isIE;
mini.isIE6 = isIE6;
mini.isIE7 = isIE7;
mini.isIE8 = isIE8;
mini.isIE9 = isIE9;
mini.isFireFox = jQuery.browser.mozilla;
mini.isOpera = jQuery.browser.opera;
mini.isSafari = jQuery.browser.safari;
mini.noBorderBox = false;
if(jQuery.boxModel == false && isIE && isIE9 == false) mini.noBorderBox = true;
mini.MouseButton = {
	Left: 0,
	Middle: 1,
	Right: 2
};
if(isIE && !isIE9) mini.MouseButton = {
	Left: 1,
	Middle: 4,
	Right: 2
};
mini._MaskID = 1;
mini._MaskObjects = {};
mini.mask = function(C) {
	var _ = Hcd(C);
	if(mini.isElement(_)) C = {
		el: _
	};
	else if(typeof C == "string") C = {
		html: C
	};
	C = mini.copyTo({
		html: "",
		cls: "",
		style: "",
		backStyle: "background:#ccc"
	}, C);
	C.el = Hcd(C.el);
	if(!C.el) C.el = document.body;
	_ = C.el;
	mini["unmask"](C.el);
	_._maskid = mini._MaskID++;
	mini._MaskObjects[_._maskid] = C;
	var $ = mini.append(_, "<div class=\"mini-mask\">" + "<div class=\"mini-mask-background\" style=\"" + C.backStyle + "\"></div>" + "<div class=\"mini-mask-msg " + C.cls + "\" style=\"" + C.style + "\">" + C.html + "</div>" + "</div>");
	C.maskEl = $;
	if(!mini.isNull(C.opacity)) mini.setOpacity($.firstChild, C.opacity);

	function A() {
		B.style.display = "block";
		var $ = mini.getSize(B);
		B.style.marginLeft = -$.width / 2 + "px";
		B.style.marginTop = -$.height / 2 + "px"
	}
	var B = $.lastChild;
	B.style.display = "none";
	setTimeout(function() {
		A()
	}, 0)
};
mini["unmask"] = function(_) {
	_ = Hcd(_);
	if(!_) _ = document.body;
	var A = mini._MaskObjects[_._maskid];
	if(!A) return;
	delete mini._MaskObjects[_._maskid];
	var $ = A.maskEl;
	A.maskEl = null;
	if($ && $.parentNode) $.parentNode.removeChild($)
};
mini.copyTo(mini, {
	treeToArray: function(C, I, J, A, $) {
		if(!I) I = "children";
		var F = [];
		for(var H = 0, D = C.length; H < D; H++) {
			var B = C[H];
			F[F.length] = B;
			if(A) B[A] = $;
			var _ = B[I];
			if(_ && _.length > 0) {
				var E = B[J],
					G = this[S6d](_, I, J, A, E);
				F.addRange(G)
			}
		}
		return F
	},
	arrayToTree: function(C, A, H, B) {
		if(!A) A = "children";
		H = H || "_id";
		B = B || "_pid";
		var G = [],
			F = {};
		for(var _ = 0, E = C.length; _ < E; _++) {
			var $ = C[_],
				I = $[H];
			if(I !== null && I !== undefined) F[I] = $;
			delete $[A]
		}
		for(_ = 0, E = C.length; _ < E; _++) {
			var $ = C[_],
				D = F[$[B]];
			if(!D) {
				G.push($);
				continue
			}
			if(!D[A]) D[A] = [];
			D[A].push($)
		}
		return G
	}
});

function UUID() {
	var A = [],
		_ = "0123456789ABCDEF".split("");
	for(var $ = 0; $ < 36; $++) A[$] = Math.floor(Math.random() * 16);
	A[14] = 4;
	A[19] = (A[19] & 3) | 8;
	for($ = 0; $ < 36; $++) A[$] = _[A[$]];
	A[8] = A[13] = A[18] = A[23] = "-";
	return A.join("")
}
String.format = function(_) {
	var $ = Array[LMj].slice[GkN](arguments, 1);
	_ = _ || "";
	return _.replace(/\{(\d+)\}/g, function(A, _) {
		return $[_]
	})
};
String[LMj].trim = function() {
	var $ = /^\s+|\s+$/g;
	return function() {
		return this.replace($, "")
	}
}();
mini.copyTo(mini, {
	measureText: function(B, _, C) {
		if(!this.measureEl) this.measureEl = mini.append(document.body, "<div></div>");
		this.measureEl.style.cssText = "position:absolute;left:-1000px;top:-1000px;visibility:hidden;";
		if(typeof B == "string") this.measureEl.className = B;
		else {
			this.measureEl.className = "";
			var G = jQuery(B),
				A = jQuery(this.measureEl),
				F = ["font-size", "font-style", "font-weight", "font-family", "line-height", "text-transform", "letter-spacing"];
			for(var $ = 0, E = F.length; $ < E; $++) {
				var D = F[$];
				A.css(D, G.css(D))
			}
		}
		if(C) Gn(this.measureEl, C);
		this.measureEl.innerHTML = _;
		return mini.getSize(this.measureEl)
	}
});
jQuery(function() {
	var $ = new Date();
	mini.isReady = true;
	mini.parse();
	AL();
	if((FrU(document.body, "overflow") == "hidden" || FrU(document.documentElement, "overflow") == "hidden") && (isIE6 || isIE7)) {
		document.body.style.overFlow = "visible";
		document.documentElement.style.overFlow = "visible"
	}
	mini.__LastWindowWidth = document.documentElement.clientWidth;
	mini.__LastWindowHeight = document.documentElement.clientHeight
});
mini_onload = function($) {
	mini.layout(null, false)
};
$v9(window, "load", mini_onload);
mini.__LastWindowWidth = document.documentElement.clientWidth;
mini.__LastWindowHeight = document.documentElement.clientHeight;
mini.doWindowResizeTimer = null;
mini.allowLayout = true;
mini_onresize = function($) {
	if(mini.doWindowResizeTimer) clearTimeout(mini.doWindowResizeTimer);
	if(Or0 == false || mini.allowLayout == false) return;
	if(typeof Ext != "undefined") mini.doWindowResizeTimer = setTimeout(function() {
		var _ = document.documentElement.clientWidth,
			$ = document.documentElement.clientHeight;
		if(mini.__LastWindowWidth == _ && mini.__LastWindowHeight == $);
		else {
			mini.__LastWindowWidth = _;
			mini.__LastWindowHeight = $;
			mini.layout(null, false)
		}
		mini.doWindowResizeTimer = null
	}, 300);
	else mini.doWindowResizeTimer = setTimeout(function() {
		var _ = document.documentElement.clientWidth,
			$ = document.documentElement.clientHeight;
		if(mini.__LastWindowWidth == _ && mini.__LastWindowHeight == $);
		else {
			mini.__LastWindowWidth = _;
			mini.__LastWindowHeight = $;
			mini.layout(null, false)
		}
		mini.doWindowResizeTimer = null
	}, 100)
};
$v9(window, "resize", mini_onresize);
mini[WH4] = function(_, A) {
	var $ = A || document.body;
	while(1) {
		if(_ == null || !_.style) return false;
		if(_ && _.style && _.style.display == "none") return false;
		if(_ == $) return true;
		_ = _.parentNode
	}
	return true
};
mini.isWindowDisplay = function() {
	try {
		var _ = window.parent,
			E = _ != window;
		if(E) {
			var C = _.document.getElementsByTagName("iframe"),
				H = _.document.getElementsByTagName("frame"),
				G = [];
			for(var $ = 0, D = C.length; $ < D; $++) G.push(C[$]);
			for($ = 0, D = H.length; $ < D; $++) G.push(H[$]);
			var B = null;
			for($ = 0, D = G.length; $ < D; $++) {
				var A = G[$];
				if(A.contentWindow == window) {
					B = A;
					break
				}
			}
			if(!B) return false;
			return mini[WH4](B, _.document.body)
		} else return true
	} catch(F) {
		return true
	}
};
Or0 = mini.isWindowDisplay();
mini.layoutIFrames = function($) {};
$.ajaxSetup({
	cache: false
});
if(isIE) setInterval(function() {
	CollectGarbage()
}, 1000);
mini_unload = function(E) {
	var D = document.getElementsByTagName("iframe");
	if(D.length > 0) for(var $ = 0, C = D.length; $ < C; $++) {
		try {
			var B = D[$];
			B.src = "";
			if(B.parentNode) B.parentNode.removeChild(B)
		} catch(E) {}
	}
	var A = mini.getComponents();
	for($ = 0, C = A.length; $ < C; $++) {
		var _ = A[$];
		_[EqU5](false)
	}
	A.length = 0;
	A = null;
	M4(window, "unload", mini_unload);
	M4(window, "load", mini_onload);
	M4(window, "resize", mini_onresize);
	mini.components = {};
	mini.classes = {};
	mini.uiClasses = {};
	try {
		CollectGarbage()
	} catch(E) {}
};
$v9(window, "unload", mini_unload);

function __OnIFrameMouseDown() {
	jQuery(document).trigger("mousedown")
}
function __BindIFrames() {
	var C = document.getElementsByTagName("iframe");
	for(var $ = 0, A = C.length; $ < A; $++) {
		var _ = C[$];
		if(_.contentWindow) {
			try {
				_.contentWindow.document.onmousedown = __OnIFrameMouseDown
			} catch(B) {}
		}
	}
}
setInterval(function() {
	__BindIFrames()
}, 500);
mini.zIndex = 1000;
mini.getMaxZIndex = function() {
	return mini.zIndex++
};
U1zK = function() {
	this._bindFields = [];
	this._bindForms = [];
	U1zK[GR_][KNT][GkN](this)
};
Iov(U1zK, SgZ, {
	bindField: function(A, D, C, B, $) {
		A = mini.get(A);
		D = mini.get(D);
		if(!A || !D || !C) return;
		var _ = {
			control: A,
			source: D,
			field: C,
			convert: $,
			mode: B
		};
		this._bindFields.push(_);
		D.on("currentchanged", this.NXD, this);
		A.on("valuechanged", this.OzPb, this)
	},
	bindForm: function(B, F, D, A) {
		B = Hcd(B);
		F = mini.get(F);
		if(!B || !F) return;
		var B = new mini.Form(B),
			$ = B.getFields();
		for(var _ = 0, E = $.length; _ < E; _++) {
			var C = $[_];
			this.bindField(C, F, C.getName(), D, A)
		}
	},
	NXD: function(H) {
		if(this._doSetting) return;
		this._doSetting = true;
		var G = H.sender,
			_ = H.record;
		for(var $ = 0, F = this._bindFields.length; $ < F; $++) {
			var B = this._bindFields[$];
			if(B.source != G) continue;
			var C = B.control,
				D = B.field;
			if(C[UD7]) if(_) {
				var A = _[D];
				C[UD7](A)
			} else C[UD7]("");
			if(C[Chg] && C.textName) if(_) C[Chg](_[C.textName]);
			else C[Chg]("")
		}
		var E = this;
		setTimeout(function() {
			E._doSetting = false
		}, 10)
	},
	OzPb: function(H) {
		if(this._doSetting) return;
		this._doSetting = true;
		var D = H.sender,
			_ = D.getValue();
		for(var $ = 0, G = this._bindFields.length; $ < G; $++) {
			var C = this._bindFields[$];
			if(C.control != D || C.mode === false) continue;
			var F = C.source,
				B = F.getCurrent();
			if(!B) continue;
			var A = {};
			A[C.field] = _;
			if(D.getText && D.textName) A[D.textName] = D.getText();
			F[W01](B, A)
		}
		var E = this;
		setTimeout(function() {
			E._doSetting = false
		}, 10)
	}
});
PC7(U1zK, "databinding");
Z4nR = function() {
	this._sources = {};
	this._data = {};
	this._links = [];
	this.VNi = {};
	Z4nR[GR_][KNT][GkN](this)
};
Iov(Z4nR, SgZ, {
	add: function(_, $) {
		if(!_ || !$) return;
		this._sources[_] = $;
		this._data[_] = [];
		$.autoCreateNewID = true;
		$.L205 = $.getIdField();
		$.PmeX = false;
		$.on("addrow", this.GD, this);
		$.on("updaterow", this.GD, this);
		$.on("deleterow", this.GD, this);
		$.on("removerow", this.GD, this);
		$.on("preload", this.Kva, this);
		$.on("selectionchanged", this.K$e, this)
	},
	addLink: function(B, _, $) {
		if(!B || !_ || !$) return;
		if(!this._sources[B] || !this._sources[_]) return;
		var A = {
			parentName: B,
			childName: _,
			parentField: $
		};
		this._links.push(A)
	},
	clearData: function() {
		this._data = {};
		this.VNi = {};
		for(var $ in this._sources) this._data = []
	},
	getData: function() {
		return this._data
	},
	_getNameByListControl: function($) {
		for(var A in this._sources) {
			var _ = this._sources[A];
			if(_ == $) return A
		}
	},
	_getRecord: function(E, _, D) {
		var B = this._data[E];
		if(!B) return false;
		for(var $ = 0, C = B.length; $ < C; $++) {
			var A = B[$];
			if(A[D] == _[D]) return A
		}
		return null
	},
	GD: function(F) {
		var C = F.type,
			_ = F.record,
			D = this._getNameByListControl(F.sender),
			E = this._getRecord(D, _, F.sender.getIdField()),
			A = this._data[D];
		if(E) {
			A = this._data[D];
			A.remove(E)
		}
		if(C == "removerow" && _._state == "added");
		else A.push(_);
		this.VNi[D] = F.sender.VNi;
		if(_._state == "added") {
			var $ = this._getParentSource(F.sender);
			if($) {
				var B = $[JFP]();
				if(B) _._parentId = B[$.getIdField()];
				else A.remove(_)
			}
		}
	},
	Kva: function(M) {
		var J = M.sender,
			L = this._getNameByListControl(J),
			K = M.sender.getIdField(),
			A = this._data[L],
			$ = {};
		for(var F = 0, C = A.length; F < C; F++) {
			var G = A[F];
			$[G[K]] = G
		}
		var N = this.VNi[L];
		if(N) J.VNi = N;
		var I = M.data || [];
		for(F = 0, C = I.length; F < C; F++) {
			var G = I[F],
				H = $[G[K]];
			if(H) {
				delete H._uid;
				mini.copyTo(G, H)
			}
		}
		var D = this._getParentSource(J);
		if(J.getPageIndex && J.getPageIndex() == 0) {
			var E = [];
			for(F = 0, C = A.length; F < C; F++) {
				G = A[F];
				if(G._state == "added") if(D) {
					var B = D[JFP]();
					if(B && B[D.getIdField()] == G._parentId) E.push(G)
				} else E.push(G)
			}
			E.reverse();
			I.insertRange(0, E)
		}
		var _ = [];
		for(F = I.length - 1; F >= 0; F--) {
			G = I[F], H = $[G[K]];
			if(H && H._state == "removed") {
				I.removeAt(F);
				_.push(H)
			}
		}
	},
	_getParentSource: function(C) {
		var _ = this._getNameByListControl(C);
		for(var $ = 0, B = this._links.length; $ < B; $++) {
			var A = this._links[$];
			if(A.childName == _) return this._sources[A.parentName]
		}
	},
	_getLinks: function(B) {
		var C = this._getNameByListControl(B),
			D = [];
		for(var $ = 0, A = this._links.length; $ < A; $++) {
			var _ = this._links[$];
			if(_.parentName == C) D.push(_)
		}
		return D
	},
	K$e: function(G) {
		var A = G.sender,
			_ = A[JFP](),
			F = this._getLinks(A);
		for(var $ = 0, E = F.length; $ < E; $++) {
			var D = F[$],
				C = this._sources[D.childName];
			if(_) {
				var B = {};
				B[D.parentField] = _[A.getIdField()];
				C.load(B)
			} else C[M3b]([])
		}
	}
});
PC7(Z4nR, "dataset");
$zd = function() {
	$zd[GR_][KNT][GkN](this)
};
Iov($zd, FIa, {
	_clearBorder: false,
	formField: true,
	value: "",
	uiCls: "mini-hidden",
	_create: function() {
		this.el = document.createElement("input");
		this.el.type = "hidden";
		this.el.className = "mini-hidden"
	},
	setName: function($) {
		this.name = $;
		this.el.name = $
	},
	setValue: function($) {
		if($ === null || $ === undefined) $ = "";
		this.el.value = $
	},
	getValue: function() {
		return this.el.value
	},
	getFormValue: function() {
		return this.getValue()
	}
});
PC7($zd, "hidden");
_s = function() {
	_s[GR_][KNT][GkN](this);
	this[YKu](false);
	this.setAllowDrag(this.allowDrag);
	this.setAllowResize(this[JkX])
};
Iov(_s, FIa, {
	_clearBorder: false,
	uiCls: "mini-popup",
	_create: function() {
		var $ = this.el = document.createElement("div");
		this.el.className = "mini-popup";
		this._contentEl = this.el
	},
	_initEvents: function() {
		IZY2(function() {
			MT(this.el, "mouseover", this.LZR, this)
		}, this)
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		_s[GR_][Fcv][GkN](this);
		this.UUPL();
		var A = this.el.childNodes;
		if(A) for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$];
			mini.layout(_)
		}
	},
	destroy: function($) {
		if(this.el) this.el.onmouseover = null;
		mini.removeChilds(this._contentEl);
		M4(document, "mousedown", this.OvM, this);
		M4(window, "resize", this.SA6y, this);
		if(this.NsDe) {
			jQuery(this.NsDe).remove();
			this.NsDe = null
		}
		if(this.shadowEl) {
			jQuery(this.shadowEl).remove();
			this.shadowEl = null
		}
		_s[GR_][EqU5][GkN](this, $)
	},
	setBody: function(_) {
		if(!_) return;
		if(!mini.isArray(_)) _ = [_];
		for(var $ = 0, A = _.length; $ < A; $++) mini.append(this._contentEl, _[$])
	},
	getAttrs: function($) {
		var A = _s[GR_][$gN][GkN](this, $);
		mini[ENl]($, A, ["popupEl", "popupCls", "showAction", "hideAction", "hAlign", "vAlign", "modalStyle", "onbeforeopen", "open", "onbeforeclose", "onclose"]);
		mini[XD9s]($, A, ["showModal", "showShadow", "allowDrag", "allowResize"]);
		mini[GGt]($, A, ["showDelay", "hideDelay", "hOffset", "vOffset", "minWidth", "minHeight", "maxWidth", "maxHeight"]);
		var _ = mini[OIAh]($, true);
		A.body = _;
		return A
	}
});
PC7(_s, "popup");
_s_prototype = {
	isPopup: false,
	popupEl: null,
	popupCls: "",
	showAction: "mouseover",
	hideAction: "outerclick",
	showDelay: 300,
	hideDelay: 500,
	hAlign: "left",
	vAlign: "below",
	hOffset: 0,
	vOffset: 0,
	minWidth: 50,
	minHeight: 25,
	maxWidth: 2000,
	maxHeight: 2000,
	showModal: false,
	showShadow: true,
	modalStyle: "opacity:0.2",
	LM6P: "mini-popup-drag",
	Tey: "mini-popup-resize",
	allowDrag: false,
	allowResize: false,
	T5XA: function() {
		if(!this.popupEl) return;
		M4(this.popupEl, "click", this.XlZ, this);
		M4(this.popupEl, "contextmenu", this.XM_, this);
		M4(this.popupEl, "mouseover", this.LZR, this)
	},
	X7: function() {
		if(!this.popupEl) return;
		$v9(this.popupEl, "click", this.XlZ, this);
		$v9(this.popupEl, "contextmenu", this.XM_, this);
		$v9(this.popupEl, "mouseover", this.LZR, this)
	},
	doShow: function(A) {
		var $ = {
			popupEl: this.popupEl,
			htmlEvent: A,
			cancel: false
		};
		this.fire("BeforeOpen", $);
		if($.cancel == true) return;
		this.fire("opening", $);
		if($.cancel == true) return;
		if(!this.popupEl) this.show();
		else {
			var _ = {};
			if(A) _.xy = [A.pageX, A.pageY];
			this.showAtEl(this.popupEl, _)
		}
	},
	doHide: function(_) {
		var $ = {
			popupEl: this.popupEl,
			htmlEvent: _,
			cancel: false
		};
		this.fire("BeforeClose", $);
		if($.cancel == true) return;
		this.close()
	},
	show: function(_, $) {
		this.showAtPos(_, $)
	},
	showAtPos: function(B, A) {
		this[Ivp](document.body);
		if(!B) B = "center";
		if(!A) A = "middle";
		this.el.style.position = "absolute";
		this.el.style.left = "-2000px";
		this.el.style.top = "-2000px";
		this.el.style.display = "";
		this.CLs();
		var _ = mini.getViewportBox(),
			$ = Y3L(this.el);
		if(B == "left") B = 0;
		if(B == "center") B = _.width / 2 - $.width / 2;
		if(B == "right") B = _.width - $.width;
		if(A == "top") A = 0;
		if(A == "middle") A = _.y + _.height / 2 - $.height / 2;
		if(A == "bottom") A = _.height - $.height;
		if(B + $.width > _.right) B = _.right - $.width;
		if(A + $.height > _.bottom) A = _.bottom - $.height;
		this.XbW(B, A)
	},
	$X9: function() {
		jQuery(this.NsDe).remove();
		if(!this[IoF]) return;
		if(this.visible == false) return;
		var $ = mini.getViewportBox();
		this.NsDe = mini.append(document.body, "<div class=\"mini-modal\"></div>");
		this.NsDe.style.height = $.height + "px";
		this.NsDe.style.width = $.width + "px";
		this.NsDe.style.zIndex = FrU(this.el, "zIndex") - 1;
		Gn(this.NsDe, this.modalStyle)
	},
	UUPL: function() {
		if(!this.shadowEl) this.shadowEl = mini.append(document.body, "<div class=\"mini-shadow\"></div>");
		this.shadowEl.style.display = this[BEZ] ? "" : "none";
		if(this[BEZ]) {
			var $ = Y3L(this.el),
				A = this.shadowEl.style;
			A.width = $.width + "px";
			A.height = $.height + "px";
			A.left = $.x + "px";
			A.top = $.y + "px";
			var _ = FrU(this.el, "zIndex");
			if(!isNaN(_)) this.shadowEl.style.zIndex = _ - 2
		}
	},
	CLs: function() {
		this.el.style.display = "";
		var $ = Y3L(this.el);
		if($.width > this.maxWidth) {
			Sbkj(this.el, this.maxWidth);
			$ = Y3L(this.el)
		}
		if($.height > this.maxHeight) {
			SmU(this.el, this.maxHeight);
			$ = Y3L(this.el)
		}
		if($.width < this.minWidth) {
			Sbkj(this.el, this.minWidth);
			$ = Y3L(this.el)
		}
		if($.height < this.minHeight) {
			SmU(this.el, this.minHeight);
			$ = Y3L(this.el)
		}
	},
	showAtEl: function(H, D) {
		H = Hcd(H);
		if(!H) return;
		if(!this.isRender() || this.el.parentNode != document.body) this[Ivp](document.body);
		var A = {
			hAlign: this.hAlign,
			vAlign: this.vAlign,
			hOffset: this.hOffset,
			vOffset: this.vOffset,
			popupCls: this.popupCls
		};
		mini.copyTo(A, D);
		V7(H, A.popupCls);
		H.popupCls = A.popupCls;
		this._popupEl = H;
		this.el.style.position = "absolute";
		this.el.style.left = "-2000px";
		this.el.style.top = "-2000px";
		this.el.style.display = "";
		this[Fcv]();
		this.CLs();
		var J = mini.getViewportBox(),
			B = Y3L(this.el),
			L = Y3L(H),
			F = A.xy,
			C = A.hAlign,
			E = A.vAlign,
			M = J.width / 2 - B.width / 2,
			K = 0;
		if(F) {
			M = F[0];
			K = F[1]
		}
		switch(A.hAlign) {
		case "outleft":
			M = L.x - B.width;
			break;
		case "left":
			M = L.x;
			break;
		case "center":
			M = L.x + L.width / 2 - B.width / 2;
			break;
		case "right":
			M = L.right - B.width;
			break;
		case "outright":
			M = L.right;
			break;
		default:
			break
		}
		switch(A.vAlign) {
		case "above":
			K = L.y - B.height;
			break;
		case "top":
			K = L.y;
			break;
		case "middle":
			K = L.y + L.height / 2 - B.height / 2;
			break;
		case "bottom":
			K = L.bottom - B.height;
			break;
		case "below":
			K = L.bottom;
			break;
		default:
			break
		}
		M = parseInt(M);
		K = parseInt(K);
		if(A.outVAlign || A.outHAlign) {
			if(A.outVAlign == "above") if(K + B.height > J.bottom) {
				var _ = L.y - J.y,
					I = J.bottom - L.bottom;
				if(_ > I) K = L.y - B.height
			}
			if(A.outHAlign == "outleft") if(M + B.width > J.right) {
				var G = L.x - J.x,
					$ = J.right - L.right;
				if(G > $) M = L.x - B.width
			}
			if(A.outHAlign == "right") if(M + B.width > J.right) M = L.right - B.width;
			this.XbW(M, K)
		} else this.showAtPos(M + A.hOffset, K + A.vOffset)
	},
	XbW: function(A, _) {
		this.el.style.display = "";
		this.el.style.zIndex = mini.getMaxZIndex();
		mini.setX(this.el, A);
		mini.setY(this.el, _);
		this[YKu](true);
		if(this.hideAction == "mouseout") $v9(document, "mousemove", this.Ew, this);
		var $ = this;
		this.UUPL();
		this.$X9();
		mini.layoutIFrames(this.el);
		this.isPopup = true;
		$v9(document, "mousedown", this.OvM, this);
		$v9(window, "resize", this.SA6y, this);
		this.fire("Open")
	},
	open: function() {
		this.show()
	},
	close: function() {
		this.hide()
	},
	hide: function() {
		if(!this.el) return;
		if(this.popupEl) HMT(this.popupEl, this.popupEl.popupCls);
		if(this._popupEl) HMT(this._popupEl, this._popupEl.popupCls);
		this._popupEl = null;
		jQuery(this.NsDe).remove();
		if(this.shadowEl) this.shadowEl.style.display = "none";
		M4(document, "mousemove", this.Ew, this);
		M4(document, "mousedown", this.OvM, this);
		M4(window, "resize", this.SA6y, this);
		this[YKu](false);
		this.isPopup = false;
		this.fire("Close")
	},
	setPopupEl: function($) {
		$ = Hcd($);
		if(!$) return;
		this.T5XA();
		this.popupEl = $;
		this.X7()
	},
	setPopupCls: function($) {
		this.popupCls = $
	},
	setShowAction: function($) {
		this.showAction = $
	},
	setHideAction: function($) {
		this.hideAction = $
	},
	setShowDelay: function($) {
		this.showDelay = $
	},
	setHideDelay: function($) {
		this.hideDelay = $
	},
	setHAlign: function($) {
		this.hAlign = $
	},
	setVAlign: function($) {
		this.vAlign = $
	},
	setHOffset: function($) {
		$ = parseInt($);
		if(isNaN($)) $ = 0;
		this.hOffset = $
	},
	setVOffset: function($) {
		$ = parseInt($);
		if(isNaN($)) $ = 0;
		this.vOffset = $
	},
	setShowModal: function($) {
		this[IoF] = $
	},
	setShowShadow: function($) {
		this[BEZ] = $
	},
	setMinWidth: function($) {
		if(isNaN($)) return;
		this.minWidth = $
	},
	setMinHeight: function($) {
		if(isNaN($)) return;
		this.minHeight = $
	},
	setMaxWidth: function($) {
		if(isNaN($)) return;
		this.maxWidth = $
	},
	setMaxHeight: function($) {
		if(isNaN($)) return;
		this.maxHeight = $
	},
	setAllowDrag: function($) {
		this.allowDrag = $;
		HMT(this.el, this.LM6P);
		if($) V7(this.el, this.LM6P)
	},
	setAllowResize: function($) {
		this[JkX] = $;
		HMT(this.el, this.Tey);
		if($) V7(this.el, this.Tey)
	},
	XlZ: function(_) {
		if(this.Ms3) return;
		if(this.showAction != "leftclick") return;
		var $ = jQuery(this.popupEl).attr("allowPopup");
		if(String($) == "false") return;
		this.doShow(_)
	},
	XM_: function(_) {
		if(this.Ms3) return;
		if(this.showAction != "rightclick") return;
		var $ = jQuery(this.popupEl).attr("allowPopup");
		if(String($) == "false") return;
		_.preventDefault();
		this.doShow(_)
	},
	LZR: function(A) {
		if(this.Ms3) return;
		if(this.showAction != "mouseover") return;
		var _ = jQuery(this.popupEl).attr("allowPopup");
		if(String(_) == "false") return;
		clearTimeout(this._hideTimer);
		this._hideTimer = null;
		if(this.isPopup) return;
		var $ = this;
		this._showTimer = setTimeout(function() {
			$.doShow(A)
		}, this.showDelay)
	},
	Ew: function($) {
		if(this.hideAction != "mouseout") return;
		this.FVe($)
	},
	OvM: function($) {
		if(this.hideAction != "outerclick") return;
		if(!this.isPopup) return;
		if(this[LU]($) || (this.popupEl && TWAc(this.popupEl, $.target)));
		else this.doHide($)
	},
	FVe: function(_) {
		if(TWAc(this.el, _.target) || (this.popupEl && TWAc(this.popupEl, _.target)));
		else {
			clearTimeout(this._showTimer);
			this._showTimer = null;
			if(this._hideTimer) return;
			var $ = this;
			this._hideTimer = setTimeout(function() {
				$.doHide(_)
			}, this.hideDelay)
		}
	},
	SA6y: function($) {
		if(this[WH4]()) this.$X9()
	}
};
mini.copyTo(_s.prototype, _s_prototype);
Has6 = function() {
	Has6[GR_][KNT][GkN](this)
};
Iov(Has6, FIa, {
	text: "",
	iconCls: "",
	iconStyle: "",
	plain: false,
	checkOnClick: false,
	checked: false,
	groupName: "",
	XH: "mini-button-plain",
	_hoverCls: "mini-button-hover",
	NOw: "mini-button-pressed",
	UFC: "mini-button-checked",
	$Sj4: "mini-button-disabled",
	allowCls: "",
	_clearBorder: false,
	set: function($) {
		if(typeof $ == "string") return this;
		this.Y5o = $.text || $[DP] || $.iconCls || $.iconPosition;
		Has6[GR_].set[GkN](this, $);
		if(this.Y5o === false) {
			this.Y5o = true;
			this[Mdk]()
		}
		return this
	},
	uiCls: "mini-button",
	_create: function() {
		this.el = document.createElement("a");
		this.el.className = "mini-button";
		this.el.hideFocus = true;
		this.el.href = "javascript:void(0)";
		this[Mdk]()
	},
	_initEvents: function() {
		IZY2(function() {
			MT(this.el, "mousedown", this.AOlf, this);
			MT(this.el, "click", this.PGY, this)
		}, this)
	},
	destroy: function($) {
		if(this.el) {
			this.el.onclick = null;
			this.el.onmousedown = null
		}
		if(this.menu) this.menu.owner = null;
		this.menu = null;
		Has6[GR_][EqU5][GkN](this, $)
	},
	doUpdate: function() {
		if(this.Y5o === false) return;
		var _ = "",
			$ = this.text;
		if(this.iconCls && $) _ = " mini-button-icon " + this.iconCls;
		else if(this.iconCls && $ === "") {
			_ = " mini-button-iconOnly " + this.iconCls;
			$ = "&nbsp;"
		}
		var A = "<span class=\"mini-button-text " + _ + "\">" + $ + "</span>";
		if(this.allowCls) A = A + "<span class=\"mini-button-allow " + this.allowCls + "\"></span>";
		this.el.innerHTML = A
	},
	href: "",
	setHref: function($) {
		this.href = $;
		this.el.href = $;
		var _ = this.el;
		setTimeout(function() {
			_.onclick = null
		}, 100)
	},
	getHref: function() {
		return this.href
	},
	target: "",
	setTarget: function($) {
		this.target = $;
		this.el.target = $
	},
	getTarget: function() {
		return this.target
	},
	setText: function($) {
		if(this.text != $) {
			this.text = $;
			this[Mdk]()
		}
	},
	getText: function() {
		return this.text
	},
	setIconCls: function($) {
		this.iconCls = $;
		this[Mdk]()
	},
	getIconCls: function() {
		return this.iconCls
	},
	setIconStyle: function($) {
		this[DP] = $;
		this[Mdk]()
	},
	getIconStyle: function() {
		return this[DP]
	},
	setIconPosition: function($) {
		this.iconPosition = "left";
		this[Mdk]()
	},
	getIconPosition: function() {
		return this.iconPosition
	},
	setPlain: function($) {
		this.plain = $;
		if($) this[QlR](this.XH);
		else this[NeI](this.XH)
	},
	getPlain: function() {
		return this.plain
	},
	setGroupName: function($) {
		this[YSqP] = $
	},
	getGroupName: function() {
		return this[YSqP]
	},
	setCheckOnClick: function($) {
		this[UmO] = $
	},
	getCheckOnClick: function() {
		return this[UmO]
	},
	setChecked: function($) {
		var _ = this.checked != $;
		this.checked = $;
		if($) this[QlR](this.UFC);
		else this[NeI](this.UFC);
		if(_) this.fire("CheckedChanged")
	},
	getChecked: function() {
		return this.checked
	},
	doClick: function() {
		this.PGY(null)
	},
	PGY: function(D) {
		if(this[RBE]()) return;
		this.focus();
		if(this[UmO]) if(this[YSqP]) {
			var _ = this[YSqP],
				C = mini.findControls(function($) {
					if($.type == "button" && $[YSqP] == _) return true
				});
			if(C.length > 0) {
				for(var $ = 0, A = C.length; $ < A; $++) {
					var B = C[$];
					if(B != this) B.setChecked(false)
				}
				this.setChecked(true)
			} else this.setChecked(!this.checked)
		} else this.setChecked(!this.checked);
		this.fire("click", {
			htmlEvent: D
		});
		return false
	},
	AOlf: function($) {
		if(this[RBE]()) return;
		this[QlR](this.NOw);
		$v9(document, "mouseup", this.V4ht, this)
	},
	V4ht: function($) {
		this[NeI](this.NOw);
		M4(document, "mouseup", this.V4ht, this)
	},
	onClick: function(_, $) {
		this.on("click", _, $)
	},
	getAttrs: function($) {
		var _ = Has6[GR_][$gN][GkN](this, $);
		_.text = $.innerHTML;
		mini[ENl]($, _, ["text", "href", "iconCls", "iconStyle", "iconPosition", "groupName", "menu", "onclick", "oncheckedchanged", "target"]);
		mini[XD9s]($, _, ["plain", "checkOnClick", "checked"]);
		return _
	}
});
PC7(Has6, "button");
PF8PButton = function() {
	PF8PButton[GR_][KNT][GkN](this)
};
Iov(PF8PButton, Has6, {
	uiCls: "mini-menubutton",
	allowCls: "mini-button-menu",
	setMenu: function($) {
		if(mini.isArray($)) $ = {
			type: "menu",
			items: $
		};
		if(typeof $ == "string") {
			var _ = Hcd($);
			if(!_) return;
			mini.parse($);
			$ = mini.get($)
		}
		if(this.menu !== $) {
			this.menu = mini.getAndCreate($);
			this.menu.setPopupEl(this.el);
			this.menu.setPopupCls("mini-button-popup");
			this.menu.setShowAction("leftclick");
			this.menu.setHideAction("outerclick");
			this.menu.setHAlign("left");
			this.menu.setVAlign("below");
			this.menu.hide();
			this.menu.owner = this
		}
	},
	setEnabled: function($) {
		this.enabled = $;
		if($) this[NeI](this.$Sj4);
		else this[QlR](this.$Sj4);
		jQuery(this.el).attr("allowPopup", !! $)
	}
});
PC7(PF8PButton, "menubutton");
mini.SplitButton = function() {
	mini.SplitButton[GR_][KNT][GkN](this)
};
Iov(mini.SplitButton, PF8PButton, {
	uiCls: "mini-splitbutton",
	allowCls: "mini-button-split"
});
PC7(mini.SplitButton, "splitbutton");
OgF = function() {
	OgF[GR_][KNT][GkN](this)
};
Iov(OgF, FIa, {
	formField: true,
	text: "",
	checked: false,
	defaultValue: false,
	trueValue: true,
	falseValue: false,
	uiCls: "mini-checkbox",
	_create: function() {
		var $ = this.uid + "$check";
		this.el = document.createElement("span");
		this.el.className = "mini-checkbox";
		this.el.innerHTML = "<input id=\"" + $ + "\" name=\"" + this.id + "\" type=\"checkbox\" class=\"mini-checkbox-check\"><label for=\"" + $ + "\" onclick=\"return false;\">" + this.text + "</label>";
		this.H7O = this.el.firstChild;
		this.Lb8 = this.el.lastChild
	},
	destroy: function($) {
		if(this.H7O) {
			this.H7O.onmouseup = null;
			this.H7O.onclick = null;
			this.H7O = null
		}
		OgF[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.KBS, this);
			this.H7O.onmouseup = function() {
				return false
			};
			var $ = this;
			this.H7O.onclick = function() {
				if($[RBE]()) return false
			}
		}, this)
	},
	setName: function($) {
		this.name = $;
		mini.setAttr(this.H7O, "name", this.name)
	},
	setText: function($) {
		if(this.text !== $) {
			this.text = $;
			this.Lb8.innerHTML = $
		}
	},
	getText: function() {
		return this.text
	},
	setChecked: function($) {
		if($ === true) $ = true;
		else if($ == this.trueValue) $ = true;
		else if($ == "true") $ = true;
		else if($ === 1) $ = true;
		else if($ == "Y") $ = true;
		else $ = false;
		if(this.checked !== $) {
			this.checked = !! $;
			this.H7O.checked = this.checked;
			this.value = this.getValue()
		}
	},
	getChecked: function() {
		return this.checked
	},
	setValue: function($) {
		if(this.checked != $) {
			this.setChecked($);
			this.value = this.getValue()
		}
	},
	getValue: function() {
		return String(this.checked == true ? this.trueValue : this.falseValue)
	},
	getFormValue: function() {
		return this.getValue()
	},
	setTrueValue: function($) {
		this.H7O.value = $;
		this.trueValue = $
	},
	getTrueValue: function() {
		return this.trueValue
	},
	setFalseValue: function($) {
		this.falseValue = $
	},
	getFalseValue: function() {
		return this.falseValue
	},
	KBS: function($) {
		if(this[RBE]()) return;
		this.setChecked(!this.checked);
		this.fire("checkedchanged", {
			checked: this.checked
		});
		this.fire("valuechanged", {
			value: this.getValue()
		});
		this.fire("click", $, this)
	},
	getAttrs: function(A) {
		var D = OgF[GR_][$gN][GkN](this, A),
			C = jQuery(A);
		D.text = A.innerHTML;
		mini[ENl](A, D, ["text", "oncheckedchanged", "onclick"]);
		mini[XD9s](A, D, ["enabled"]);
		var B = mini.getAttr(A, "checked");
		if(B) D.checked = (B == "true" || B == "checked") ? true : false;
		var _ = C.attr("trueValue");
		if(_) {
			D.trueValue = _;
			_ = parseInt(_);
			if(!isNaN(_)) D.trueValue = _
		}
		var $ = C.attr("falseValue");
		if($) {
			D.falseValue = $;
			$ = parseInt($);
			if(!isNaN($)) D.falseValue = $
		}
		return D
	}
});
PC7(OgF, "checkbox");
OuY = function() {
	OuY[GR_][KNT][GkN](this);
	var $ = this[RBE]();
	if($ || this.allowInput == false) this.IJ6[Egr] = true;
	if(this.enabled == false) this[QlR](this.$Sj4);
	if($) this[QlR](this.O1O);
	if(this.required) this[QlR](this.S2)
};
Iov(OuY, CLi, {
	name: "",
	formField: true,
	defaultValue: "",
	value: "",
	text: "",
	emptyText: "",
	maxLength: 1000,
	minLength: 0,
	width: 125,
	height: 21,
	allowInput: true,
	Tf4: "mini-buttonedit-noInput",
	O1O: "mini-buttonedit-readOnly",
	$Sj4: "mini-buttonedit-disabled",
	Er7C: "mini-buttonedit-empty",
	Ppho: "mini-buttonedit-focus",
	DPjh: "mini-buttonedit-button",
	ME: "mini-buttonedit-button-hover",
	WCL: "mini-buttonedit-button-pressed",
	set: function($) {
		if(typeof $ == "string") return this;
		this.Y5o = !($.enabled == false || $.allowInput == false || $[Egr]);
		OuY[GR_].set[GkN](this, $);
		if(this.Y5o === false) {
			this.Y5o = true;
			this[Mdk]()
		}
		return this
	},
	uiCls: "mini-buttonedit",
	DpiHtml: function() {
		var $ = "onmouseover=\"V7(this,'" + this.ME + "');\" " + "onmouseout=\"HMT(this,'" + this.ME + "');\"";
		return "<span class=\"mini-buttonedit-button\" " + $ + "><span class=\"mini-buttonedit-icon\"></span></span>"
	},
	_create: function() {
		this.el = document.createElement("span");
		this.el.className = "mini-buttonedit";
		var $ = this.DpiHtml();
		this.el.innerHTML = "<span class=\"mini-buttonedit-border\"><input type=\"input\" class=\"mini-buttonedit-input\" autocomplete=\"off\"/>" + $ + "</span><input name=\"" + this.name + "\" type=\"hidden\"/>";
		this.TG = this.el.firstChild;
		this.IJ6 = this.TG.firstChild;
		this.HRA = this.el.lastChild;
		this._buttonEl = this.TG.lastChild
	},
	destroy: function($) {
		if(this.el) {
			this.el.onmousedown = null;
			this.el.onmousewheel = null;
			this.el.onmouseover = null;
			this.el.onmouseout = null
		}
		if(this.IJ6) {
			this.IJ6.onchange = null;
			this.IJ6.onfocus = null;
			mini[J9](this.IJ6);
			this.IJ6 = null
		}
		OuY[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		IZY2(function() {
			MT(this.el, "mousedown", this.AOlf, this);
			MT(this.IJ6, "focus", this.OS6, this);
			MT(this.IJ6, "change", this.SDs, this)
		}, this)
	},
	AAi: false,
	YV4: function() {
		if(this.AAi) return;
		this.AAi = true;
		$v9(this.el, "click", this.PGY, this);
		$v9(this.IJ6, "blur", this.Na3, this);
		$v9(this.IJ6, "keydown", this.KPW, this);
		$v9(this.IJ6, "keyup", this.KMzd, this);
		$v9(this.IJ6, "keypress", this.TG41, this)
	},
	_buttonWidth: 20,
	doLayout: function() {
		if(!this.canLayout()) return;
		OuY[GR_][Fcv][GkN](this);
		var $ = R0(this.el);
		if(this.Izgy) $ -= 18;
		$ -= 2;
		this.TG.style.width = $ + "px";
		$ -= this._buttonWidth;
		if(this.el.style.width == "100%") $ -= 1;
		if($ < 0) $ = 0;
		this.IJ6.style.width = $ + "px"
	},
	setHeight: function($) {
		if(parseInt($) == $) $ += "px";
		this.height = $
	},
	DwrN: function() {},
	focus: function() {
		try {
			this.IJ6.focus();
			var $ = this;
			setTimeout(function() {
				if($._j) $.IJ6.focus()
			}, 10)
		} catch(_) {}
	},
	blur: function() {
		try {
			this.IJ6.blur()
		} catch($) {}
	},
	selectText: function() {
		this.IJ6[M$]()
	},
	getTextEl: function() {
		return this.IJ6
	},
	setName: function($) {
		this.name = $;
		this.HRA.name = $
	},
	setEmptyText: function($) {
		if($ === null || $ === undefined) $ = "";
		this[QGN] = $;
		this.DwrN()
	},
	getEmptyText: function() {
		return this[QGN]
	},
	setText: function($) {
		if($ === null || $ === undefined) $ = "";
		var _ = this.text !== $;
		this.text = $;
		this.IJ6.value = $
	},
	getText: function() {
		var $ = this.IJ6.value;
		return $ != this[QGN] ? $ : ""
	},
	setValue: function($) {
		if($ === null || $ === undefined) $ = "";
		var _ = this.value !== $;
		this.value = $;
		this.DwrN()
	},
	getValue: function() {
		return this.value
	},
	getFormValue: function() {
		value = this.value;
		if(value === null || value === undefined) value = "";
		return String(value)
	},
	setMaxLength: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this.maxLength = $;
		this.IJ6.maxLength = $
	},
	getMaxLength: function() {
		return this.maxLength
	},
	setMinLength: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this.minLength = $
	},
	getMinLength: function() {
		return this.minLength
	},
	_doReadOnly: function() {
		var $ = this[RBE]();
		if($ || this.allowInput == false) this.IJ6[Egr] = true;
		else this.IJ6[Egr] = false;
		if($) this[QlR](this.O1O);
		else this[NeI](this.O1O);
		if(this.allowInput) this[NeI](this.Tf4);
		else this[QlR](this.Tf4)
	},
	setAllowInput: function($) {
		this.allowInput = $;
		this._doReadOnly()
	},
	getAllowInput: function() {
		return this.allowInput
	},
	Izgy: null,
	getErrorIconEl: function() {
		if(!this.Izgy) this.Izgy = mini.append(this.el, "<span class=\"mini-errorIcon\"></span>");
		return this.Izgy
	},
	Kg: function() {
		if(this.Izgy) {
			var $ = this.Izgy;
			jQuery($).remove()
		}
		this.Izgy = null
	},
	PGY: function($) {
		if(this[RBE]() || this.enabled == false) return;
		if(TWAc(this._buttonEl, $.target)) this.Ii($)
	},
	AOlf: function(B) {
		if(this[RBE]() || this.enabled == false) return;
		if(!TWAc(this.IJ6, B.target)) {
			var $ = this;
			setTimeout(function() {
				$.focus();
				mini.selectRange($.IJ6, 1000, 1000)
			}, 1);
			if(TWAc(this._buttonEl, B.target)) {
				var _ = ZW(B.target, "mini-buttonedit-up"),
					A = ZW(B.target, "mini-buttonedit-down");
				if(_) {
					V7(_, this.WCL);
					this.Swk(B, "up")
				} else if(A) {
					V7(A, this.WCL);
					this.Swk(B, "down")
				} else {
					V7(this._buttonEl, this.WCL);
					this.Swk(B)
				}
				$v9(document, "mouseup", this.V4ht, this)
			}
		}
	},
	V4ht: function(_) {
		var $ = this;
		setTimeout(function() {
			var A = $._buttonEl.getElementsByTagName("*");
			for(var _ = 0, B = A.length; _ < B; _++) HMT(A[_], $.WCL);
			HMT($._buttonEl, $.WCL);
			HMT($.el, $.NOw)
		}, 80);
		M4(document, "mouseup", this.V4ht, this)
	},
	OS6: function($) {
		this[Mdk]();
		this.YV4();
		if(this[RBE]()) return;
		this._j = true;
		this[QlR](this.Ppho)
	},
	Na3: function(_) {
		this._j = false;
		var $ = this;
		setTimeout(function() {
			if($._j == false) $[NeI]($.Ppho)
		}, 2)
	},
	KPW: function(_) {
		this.fire("keydown", {
			htmlEvent: _
		});
		if(_.keyCode == 8 && (this[RBE]() || this.allowInput == false)) return false;
		if(_.keyCode == 13) {
			var $ = this;
			$.SDs(null);
			$.fire("enter")
		}
	},
	SDs: function() {},
	KMzd: function($) {
		this.fire("keyup", {
			htmlEvent: $
		})
	},
	TG41: function($) {
		this.fire("keypress", {
			htmlEvent: $
		})
	},
	Ii: function($) {
		var _ = {
			htmlEvent: $,
			cancel: false
		};
		this.fire("beforebuttonclick", _);
		if(_.cancel == true) return;
		this.fire("buttonclick", _)
	},
	Swk: function(_, $) {
		this.focus();
		this[QlR](this.Ppho);
		this.fire("buttonmousedown", {
			htmlEvent: _,
			spinType: $
		})
	},
	onButtonClick: function(_, $) {
		this.on("buttonclick", _, $)
	},
	onButtonMouseDown: function(_, $) {
		this.on("buttonmousedown", _, $)
	},
	onTextChanged: function(_, $) {
		this.on("textchanged", _, $)
	},
	textName: "",
	setTextName: function($) {
		this.textName = $;
		if(this.IJ6) mini.setAttr(this.IJ6, "name", this.textName)
	},
	getTextName: function() {
		return this.textName
	},
	getAttrs: function($) {
		var A = OuY[GR_][$gN][GkN](this, $),
			_ = jQuery($);
		mini[ENl]($, A, ["value", "text", "textName", "onenter", "onkeydown", "onkeyup", "onkeypress", "onbuttonclick", "onbuttonmousedown", "ontextchanged"]);
		mini[XD9s]($, A, ["allowInput"]);
		mini[GGt]($, A, ["maxLength", "minLength"]);
		return A
	}
});
PC7(OuY, "buttonedit");
Pg3R = function() {
	Pg3R[GR_][KNT][GkN](this)
};
Iov(Pg3R, CLi, {
	name: "",
	formField: true,
	minHeight: 15,
	emptyText: "",
	text: "",
	value: "",
	defaultValue: "",
	width: 125,
	height: 21,
	Er7C: "mini-textbox-empty",
	Ppho: "mini-textbox-focus",
	$Sj4: "mini-textbox-disabled",
	uiCls: "mini-textbox",
	$tl: "text",
	_create: function() {
		var $ = "<input type=\"" + this.$tl + "\" class=\"mini-textbox-input\" autocomplete=\"off\"/>";
		if(this.$tl == "textarea") $ = "<textarea class=\"mini-textbox-input\" autocomplete=\"off\"/></textarea>";
		$ += "<input type=\"hidden\"/>";
		this.el = document.createElement("span");
		this.el.className = "mini-textbox";
		this.el.innerHTML = $;
		this.IJ6 = this.el.firstChild;
		this.HRA = this.el.lastChild;
		this.TG = this.IJ6
	},
	_initEvents: function() {
		IZY2(function() {
			MT(this.IJ6, "drop", this.__OnDropText, this);
			MT(this.IJ6, "change", this.SDs, this);
			MT(this.IJ6, "focus", this.OS6, this);
			MT(this.el, "mousedown", this.AOlf, this)
		}, this);
		this.on("validation", this.H8, this)
	},
	AAi: false,
	YV4: function() {
		if(this.AAi) return;
		this.AAi = true;
		$v9(this.IJ6, "blur", this.Na3, this);
		$v9(this.IJ6, "keydown", this.KPW, this);
		$v9(this.IJ6, "keyup", this.KMzd, this);
		$v9(this.IJ6, "keypress", this.TG41, this)
	},
	destroy: function($) {
		if(this.el) this.el.onmousedown = null;
		if(this.IJ6) {
			this.IJ6.ondrop = null;
			this.IJ6.onchange = null;
			this.IJ6.onfocus = null;
			mini[J9](this.IJ6);
			this.IJ6 = null
		}
		if(this.HRA) {
			mini[J9](this.HRA);
			this.HRA = null
		}
		Pg3R[GR_][EqU5][GkN](this, $)
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		var $ = R0(this.el);
		if(this.Izgy) $ -= 18;
		$ -= 4;
		if(this.el.style.width == "100%") $ -= 1;
		if($ < 0) $ = 0;
		this.IJ6.style.width = $ + "px"
	},
	setHeight: function($) {
		if(parseInt($) == $) $ += "px";
		this.height = $;
		if(this.$tl == "textarea") {
			this.el.style.height = $;
			this[Fcv]()
		}
	},
	setName: function($) {
		if(this.name != $) {
			this.name = $;
			this.HRA.name = $
		}
	},
	setValue: function($) {
		if($ === null || $ === undefined) $ = "";
		$ = String($);
		if(this.value !== $) {
			this.value = $;
			this.HRA.value = this.IJ6.value = $;
			this.DwrN()
		}
	},
	getValue: function() {
		return this.value
	},
	getFormValue: function() {
		value = this.value;
		if(value === null || value === undefined) value = "";
		return String(value)
	},
	setAllowInput: function($) {
		if(this.allowInput != $) {
			this.allowInput = $;
			this[Mdk]()
		}
	},
	getAllowInput: function() {
		return this.allowInput
	},
	DwrN: function() {
		if(this._j) return;
		if(this.value == "" && this[QGN]) {
			this.IJ6.value = this[QGN];
			V7(this.el, this.Er7C)
		} else HMT(this.el, this.Er7C)
	},
	setEmptyText: function($) {
		if(this[QGN] != $) {
			this[QGN] = $;
			this.DwrN()
		}
	},
	getEmptyText: function() {
		return this[QGN]
	},
	setReadOnly: function($) {
		if(this[Egr] != $) {
			this[Egr] = $;
			this[Mdk]()
		}
	},
	setEnabled: function($) {
		if(this.enabled != $) {
			this.enabled = $;
			this[Mdk]()
		}
	},
	doUpdate: function() {
		if(this.enabled) this[NeI](this.$Sj4);
		else this[QlR](this.$Sj4);
		if(this[RBE]() || this.allowInput == false) this.IJ6[Egr] = true;
		else this.IJ6[Egr] = false;
		if(this.required) this[QlR](this.S2);
		else this[NeI](this.S2)
	},
	focus: function() {
		try {
			this.IJ6.focus()
		} catch($) {}
	},
	blur: function() {
		try {
			this.IJ6.blur()
		} catch($) {}
	},
	selectText: function() {
		this.IJ6[M$]()
	},
	getTextEl: function() {
		return this.IJ6
	},
	Izgy: null,
	getErrorIconEl: function() {
		if(!this.Izgy) this.Izgy = mini.append(this.el, "<span class=\"mini-errorIcon\"></span>");
		return this.Izgy
	},
	Kg: function() {
		if(this.Izgy) {
			var $ = this.Izgy;
			jQuery($).remove()
		}
		this.Izgy = null
	},
	AOlf: function(_) {
		var $ = this;
		if(!TWAc(this.IJ6, _.target)) setTimeout(function() {
			$.focus();
			mini.selectRange($.IJ6, 1000, 1000)
		}, 1);
		else setTimeout(function() {
			$.IJ6.focus()
		}, 1)
	},
	SDs: function(A, _) {
		var $ = this.value;
		this[UD7](this.IJ6.value);
		if($ !== this.getValue() || _ === true) this.PV2()
	},
	__OnDropText: function(_) {
		var $ = this;
		setTimeout(function() {
			$.SDs(_)
		}, 0)
	},
	KPW: function(_) {
		this.fire("keydown", {
			htmlEvent: _
		});
		if(_.keyCode == 8 && (this[RBE]() || this.allowInput == false)) return false;
		if(_.keyCode == 13) {
			this.SDs(null, true);
			var $ = this;
			setTimeout(function() {
				$.fire("enter")
			}, 10)
		}
	},
	KMzd: function($) {
		this.fire("keyup", {
			htmlEvent: $
		})
	},
	TG41: function($) {
		this.fire("keypress", {
			htmlEvent: $
		})
	},
	OS6: function($) {
		this[Mdk]();
		if(this[RBE]()) return;
		this._j = true;
		this[QlR](this.Ppho);
		this.YV4();
		HMT(this.el, this.Er7C);
		if(this[QGN] && this.IJ6.value == this[QGN]) {
			this.IJ6.value = "";
			this.IJ6[M$]()
		}
	},
	Na3: function(_) {
		this._j = false;
		var $ = this;
		setTimeout(function() {
			if($._j == false) $[NeI]($.Ppho)
		}, 2);
		if(this[QGN] && this.IJ6.value == "") {
			this.IJ6.value = this[QGN];
			V7(this.el, this.Er7C)
		}
	},
	getAttrs: function($) {
		var A = Pg3R[GR_][$gN][GkN](this, $),
			_ = jQuery($);
		mini[ENl]($, A, ["value", "text", "emptyText", "onenter", "onkeydown", "onkeyup", "onkeypress", "maxLengthErrorText", "minLengthErrorText", "vtype", "emailErrorText", "urlErrorText", "floatErrorText", "intErrorText", "dateErrorText", "minErrorText", "maxErrorText", "rangeLengthErrorText", "rangeErrorText", "rangeCharErrorText"]);
		mini[XD9s]($, A, ["allowInput"]);
		mini[GGt]($, A, ["maxLength", "minLength", "minHeight"]);
		return A
	},
	vtype: "",
	emailErrorText: "Please enter a valid email address.",
	urlErrorText: "Please enter a valid URL.",
	floatErrorText: "Please enter a valid number.",
	intErrorText: "Please enter only digits",
	dateErrorText: "Please enter a valid date. Date format is {0}",
	maxLengthErrorText: "Please enter no more than {0} characters.",
	minLengthErrorText: "Please enter at least {0} characters.",
	maxErrorText: "Please enter a value less than or equal to {0}.",
	minErrorText: "Please enter a value greater than or equal to {0}.",
	rangeLengthErrorText: "Please enter a value between {0} and {1} characters long.",
	rangeCharErrorText: "Please enter a value between {0} and {1} characters long.",
	rangeErrorText: "Please enter a value between {0} and {1}.",
	H8: function(H) {
		if(H.isValid == false) return;
		var _ = this.vtype.split(";");
		for(var $ = 0, F = _.length; $ < F; $++) {
			var B = _[$].trim(),
				E = B.split(":"),
				C = E[0],
				A = E[1];
			if(A) A = A.split(",");
			else A = [];
			var G = this["__" + C];
			if(G) {
				var D = G(H.value, A);
				if(D !== true) {
					H.isValid = false;
					H.errorText = this[E[0] + "ErrorText"] || "";
					H.errorText = String.format(H.errorText, A[0], A[1], A[2], A[3], A[4]);
					break
				}
			}
		}
	},
	__email: function(_, $) {
		if(_.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1) return true;
		else return false
	},
	__url: function(A, $) {
		function _(_) {
			_ = _.toLowerCase();
			var $ = "^((https|http|ftp|rtsp|mms)?://)" + "?(([0-9a-z_!~*'().&=+$%-]+:)?[0-9a-z_!~*'().&=+$%-]+@)?" + "(([0-9]{1,3}.){3}[0-9]{1,3}" + "|" + "([0-9a-z_!~*'()-]+.)*" + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]." + "[a-z]{2,6})" + "(:[0-9]{1,4})?" + "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$",
				A = new RegExp($);
			if(A.test(_)) return(true);
			else return(false)
		}
		return _(A)
	},
	__int: function(A, _) {
		function $(_) {
			var $ = String(_);
			return $.length > 0 && !(/[^0-9]/).test($)
		}
		return $(A)
	},
	__float: function(A, _) {
		function $(_) {
			var $ = String(_);
			return $.length > 0 && !(/[^0-9.]/).test($) && (/\.\d/).test($)
		}
		return $(A)
	},
	__date: function(B, _) {
		if(!B) return false;
		var $ = null,
			A = _[0];
		if(A) {
			$ = mini.parseDate(B, A);
			if($ && $.getFullYear) if(mini.formatDate($, A) == B) return true
		} else {
			$ = mini.parseDate(B, "yyyy-MM-dd");
			if(!$) $ = mini.parseDate(B, "yyyy/MM/dd");
			if(!$) $ = mini.parseDate(B, "MM/dd/yyyy");
			if($ && $.getFullYear) return true
		}
		return false
	},
	__maxLength: function(A, $) {
		var _ = parseInt($);
		if(!A || isNaN(_)) return true;
		if(A.length <= _) return true;
		else return false
	},
	__minLength: function(A, $) {
		var _ = parseInt($);
		if(isNaN(_)) return true;
		if(A.length >= _) return true;
		else return false
	},
	__rangeLength: function(B, _) {
		if(!B) return false;
		var $ = parseFloat(_[0]),
			A = parseFloat(_[1]);
		if(isNaN($) || isNaN(A)) return true;
		if($ <= B.length && B.length <= A) return true;
		return false
	},
	__rangeChar: function(G, B) {
		if(!G) return false;
		var A = parseFloat(B[0]),
			E = parseFloat(B[1]);
		if(isNaN(A) || isNaN(E)) return true;

		function C(_) {
			var $ = new RegExp("^[\u4e00-\u9fa5]+$");
			if($.test(_)) return true;
			return false
		}
		var $ = 0,
			F = String(G).split("");
		for(var _ = 0, D = F.length; _ < D; _++) if(C(F[_])) $ += 2;
		else $ += 1;
		if(A <= $ && $ <= E) return true;
		return false
	},
	__range: function(B, _) {
		B = parseFloat(B);
		if(isNaN(B)) return false;
		var $ = parseFloat(_[0]),
			A = parseFloat(_[1]);
		if(isNaN($) || isNaN(A)) return true;
		if($ <= B && B <= A) return true;
		return false
	},
	setVtype: function($) {
		this.vtype = $
	},
	getVtype: function() {
		return this.vtype
	},
	setEmailErrorText: function($) {
		this.emailErrorText = $
	},
	getEmailErrorText: function() {
		return this.emailErrorText
	},
	setUrlErrorText: function($) {
		this.urlErrorText = $
	},
	getUrlErrorText: function() {
		return this.urlErrorText
	},
	setFloatErrorText: function($) {
		this.floatErrorText = $
	},
	getFloatErrorText: function() {
		return this.floatErrorText
	},
	setIntErrorText: function($) {
		this.intErrorText = $
	},
	getIntErrorText: function() {
		return this.intErrorText
	},
	setDateErrorText: function($) {
		this.dateErrorText = $
	},
	getDateErrorText: function() {
		return this.dateErrorText
	},
	setMaxLengthErrorText: function($) {
		this.maxLengthErrorText = $
	},
	getMaxLengthErrorText: function() {
		return this.maxLengthErrorText
	},
	setMinLengthErrorText: function($) {
		this.minLengthErrorText = $
	},
	getMinLengthErrorText: function() {
		return this.minLengthErrorText
	},
	setMaxErrorText: function($) {
		this.maxErrorText = $
	},
	getMaxErrorText: function() {
		return this.maxErrorText
	},
	setMinErrorText: function($) {
		this.minErrorText = $
	},
	getMinErrorText: function() {
		return this.minErrorText
	},
	setRangeLengthErrorText: function($) {
		this.rangeLengthErrorText = $
	},
	getRangeLengthErrorText: function() {
		return this.rangeLengthErrorText
	},
	setRangeCharErrorText: function($) {
		this.rangeCharErrorText = $
	},
	getRangeCharErrorText: function() {
		return this.rangeCharErrorText
	},
	setRangeErrorText: function($) {
		this.rangeErrorText = $
	},
	getRangeErrorText: function() {
		return this.rangeErrorText
	}
});
PC7(Pg3R, "textbox");
WF0 = function() {
	WF0[GR_][KNT][GkN](this)
};
Iov(WF0, Pg3R, {
	uiCls: "mini-password",
	$tl: "password",
	setEmptyText: function($) {
		this[QGN] = ""
	}
});
PC7(WF0, "password");
Q47 = function() {
	Q47[GR_][KNT][GkN](this)
};
Iov(Q47, Pg3R, {
	maxLength: 1000000,
	width: 180,
	height: 50,
	minHeight: 50,
	$tl: "textarea",
	uiCls: "mini-textarea",
	doLayout: function() {
		if(!this.canLayout()) return;
		Q47[GR_][Fcv][GkN](this);
		var $ = KwY(this.el);
		$ -= 2;
		if($ < 0) $ = 0;
		this.IJ6.style.height = $ + "px"
	}
});
PC7(Q47, "textarea");
AU6v = function() {
	AU6v[GR_][KNT][GkN](this);
	this.GP8v();
	this.el.className += " mini-popupedit"
};
Iov(AU6v, OuY, {
	uiCls: "mini-popupedit",
	popup: null,
	popupCls: "mini-buttonedit-popup",
	_hoverCls: "mini-buttonedit-hover",
	NOw: "mini-buttonedit-pressed",
	destroy: function($) {
		if(this.isShowPopup()) this[OsRe]();
		if(this.popup) {
			this.popup[EqU5]();
			this.popup = null
		}
		AU6v[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		AU6v[GR_][S1B][GkN](this);
		IZY2(function() {
			MT(this.el, "mouseover", this.LZR, this);
			MT(this.el, "mouseout", this.$MHa, this)
		}, this)
	},
	JOG: function() {
		this.buttons = [];
		var $ = this.createButton({
			cls: "mini-buttonedit-popup",
			iconCls: "mini-buttonedit-icons-popup",
			name: "popup"
		});
		this.buttons.push($)
	},
	LZR: function($) {
		if(this[RBE]() || this.allowInput) return;
		if(ZW($.target, "mini-buttonedit-border")) this[QlR](this._hoverCls)
	},
	$MHa: function($) {
		if(this[RBE]() || this.allowInput) return;
		this[NeI](this._hoverCls)
	},
	AOlf: function($) {
		if(this[RBE]()) return;
		AU6v[GR_].AOlf[GkN](this, $);
		if(this.allowInput == false && ZW($.target, "mini-buttonedit-border")) {
			V7(this.el, this.NOw);
			$v9(document, "mouseup", this.V4ht, this)
		}
	},
	KPW: function($) {
		this.fire("keydown", {
			htmlEvent: $
		});
		if($.keyCode == 8 && (this[RBE]() || this.allowInput == false)) return false;
		if($.keyCode == 9) {
			this[OsRe]();
			return
		}
		if($.keyCode == 27) {
			this[OsRe]();
			return
		}
		if($.keyCode == 13) this.fire("enter");
		if(this.isShowPopup()) if($.keyCode == 13 || $.keyCode == 27) $.stopPropagation()
	},
	within: function($) {
		if(TWAc(this.el, $.target)) return true;
		if(this.popup[LU]($)) return true;
		return false
	},
	popupWidth: "100%",
	popupMinWidth: 50,
	popupMaxWidth: 2000,
	popupHeight: "",
	popupMinHeight: 30,
	popupMaxHeight: 2000,
	setPopup: function($) {
		if(typeof $ == "string") {
			mini.parse($);
			$ = mini.get($)
		}
		var _ = mini.getAndCreate($);
		if(!_) return;
		_[YKu](true);
		_[Ivp](this.popup._contentEl);
		_.owner = this;
		_.on("beforebuttonclick", this.M1d, this)
	},
	getPopup: function() {
		if(!this.popup) this.GP8v();
		return this.popup
	},
	GP8v: function() {
		this.popup = new _s();
		this.popup.setShowAction("none");
		this.popup.setHideAction("outerclick");
		this.popup.setPopupEl(this.el);
		this.popup.on("BeforeClose", this.U0BN, this);
		$v9(this.popup.el, "keydown", this.__OnPopupKeyDown, this)
	},
	U0BN: function($) {
		if(this[LU]($.htmlEvent)) $.cancel = true
	},
	__OnPopupKeyDown: function($) {},
	showPopup: function() {
		var _ = this.getPopup(),
			B = this.getBox(),
			$ = this[Q_D];
		if(this[Q_D] == "100%") $ = B.width;
		_[_wJ]($);
		var A = parseInt(this[Dzv]);
		if(!isNaN(A)) _[P4](A);
		else _[P4]("auto");
		_.setMinWidth(this[_0]);
		_.setMinHeight(this[Jrd]);
		_.setMaxWidth(this[VTHw]);
		_.setMaxHeight(this[OsZw]);
		_.showAtEl(this.el, {
			hAlign: "left",
			vAlign: "below",
			outVAlign: "above",
			outHAlign: "right",
			popupCls: this.popupCls
		});
		_.on("Close", this.EVH, this);
		this.fire("showpopup")
	},
	EVH: function($) {
		this.fire("hidepopup")
	},
	hidePopup: function() {
		var $ = this.getPopup();
		$.close()
	},
	isShowPopup: function() {
		if(this.popup && this.popup.visible) return true;
		else return false
	},
	setPopupWidth: function($) {
		this[Q_D] = $
	},
	setPopupMaxWidth: function($) {
		this[VTHw] = $
	},
	setPopupMinWidth: function($) {
		this[_0] = $
	},
	getPopupWidth: function($) {
		return this[Q_D]
	},
	getPopupMaxWidth: function($) {
		return this[VTHw]
	},
	getPopupMinWidth: function($) {
		return this[_0]
	},
	setPopupHeight: function($) {
		this[Dzv] = $
	},
	setPopupMaxHeight: function($) {
		this[OsZw] = $
	},
	setPopupMinHeight: function($) {
		this[Jrd] = $
	},
	getPopupHeight: function($) {
		return this[Dzv]
	},
	getPopupMaxHeight: function($) {
		return this[OsZw]
	},
	getPopupMinHeight: function($) {
		return this[Jrd]
	},
	PGY: function(_) {
		if(this[RBE]()) return;
		if(TWAc(this._buttonEl, _.target)) this.Ii(_);
		if(this.allowInput == false || TWAc(this._buttonEl, _.target)) if(this.isShowPopup()) this[OsRe]();
		else {
			var $ = this;
			setTimeout(function() {
				$[Zpt]()
			}, 1)
		}
	},
	M1d: function($) {
		if($.name == "close") this[OsRe]();
		$.cancel = true
	},
	getAttrs: function($) {
		var _ = AU6v[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["popupWidth", "popupHeight", "popup", "onshowpopup", "onhidepopup"]);
		mini[GGt]($, _, ["popupMinWidth", "popupMaxWidth", "popupMinHeight", "popupMaxHeight"]);
		return _
	}
});
PC7(AU6v, "popupedit");
ZjG = function() {
	this.data = [];
	this.columns = [];
	ZjG[GR_][KNT][GkN](this)
};
Iov(ZjG, AU6v, {
	text: "",
	value: "",
	valueField: "id",
	textField: "text",
	delimiter: ",",
	multiSelect: false,
	data: [],
	url: "",
	columns: [],
	allowInput: false,
	popupMaxHeight: 200,
	set: function(A) {
		if(typeof A == "string") return this;
		var $ = A.value;
		delete A.value;
		var B = A.url;
		delete A.url;
		var _ = A.data;
		delete A.data;
		ZjG[GR_].set[GkN](this, A);
		if(!mini.isNull(_)) {
			this[OUQ](_);
			A.data = _
		}
		if(!mini.isNull(B)) {
			this.setUrl(B);
			A.url = B
		}
		if(!mini.isNull($)) {
			this[UD7]($);
			A.value = $
		}
		return this
	},
	uiCls: "mini-combobox",
	GP8v: function() {
		ZjG[GR_].GP8v[GkN](this);
		this.YkMq = new YTBL();
		this.YkMq.setBorderStyle("border:0;");
		this.YkMq.setStyle("width:100%;height:auto;");
		this.YkMq[Ivp](this.popup._contentEl);
		this.YkMq.on("itemclick", this.VsW, this)
	},
	showPopup: function() {
		this.YkMq[P4]("auto");
		ZjG[GR_][Zpt][GkN](this);
		var $ = this.popup.el.style.height;
		if($ == "" || $ == "auto") this.YkMq[P4]("auto");
		else this.YkMq[P4]("100%");
		this.YkMq[UD7](this.value)
	},
	getItem: function($) {
		return typeof $ == "object" ? $ : this.data[$]
	},
	indexOf: function($) {
		return this.data.indexOf($)
	},
	getAt: function($) {
		return this.data[$]
	},
	load: function($) {
		if(typeof $ == "string") this.setUrl($);
		else this[OUQ]($)
	},
	setData: function(data) {
		if(typeof data == "string") data = eval("(" + data + ")");
		if(!mini.isArray(data)) data = [];
		this.YkMq[OUQ](data);
		this.data = this.YkMq.data;
		var vts = this.YkMq.B5fK(this.value);
		this.IJ6.value = vts[1]
	},
	getData: function() {
		return this.data
	},
	setUrl: function(_) {
		this.getPopup();
		this.YkMq.setUrl(_);
		this.url = this.YkMq.url;
		this.data = this.YkMq.data;
		var $ = this.YkMq.B5fK(this.value);
		this.IJ6.value = $[1]
	},
	getUrl: function() {
		return this.url
	},
	setValueField: function($) {
		this[B$Gk] = $;
		if(this.YkMq) this.YkMq[$J]($)
	},
	getValueField: function() {
		return this[B$Gk]
	},
	setTextField: function($) {
		if(this.YkMq) this.YkMq.setTextField($);
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setDisplayField: function($) {
		this.setTextField($)
	},
	setValue: function($) {
		if(this.value !== $) {
			var _ = this.YkMq.B5fK($);
			this.value = $;
			this.HRA.value = this.value;
			this.IJ6.value = _[1]
		} else {
			_ = this.YkMq.B5fK($);
			this.IJ6.value = _[1]
		}
	},
	setMultiSelect: function($) {
		if(this[V0] != $) {
			this[V0] = $;
			if(this.YkMq) {
				this.YkMq.setMultiSelect($);
				this.YkMq.setShowCheckBox($)
			}
		}
	},
	getMultiSelect: function() {
		return this[V0]
	},
	setColumns: function($) {
		if(!mini.isArray($)) $ = [];
		this.columns = $;
		this.YkMq[Z9i]($)
	},
	getColumns: function() {
		return this.columns
	},
	showNullItem: false,
	setShowNullItem: function($) {
		if(this.showNullItem != $) {
			this.showNullItem = $;
			this.YkMq.setShowNullItem($)
		}
	},
	getShowNullItem: function() {
		return this.showNullItem
	},
	PV2: function() {
		if(this.validateOnChanged) this[UzD]();
		var $ = this.getValue(),
			B = this.getSelecteds(),
			_ = B[0],
			A = this;
		A.fire("valuechanged", {
			value: $,
			selecteds: B,
			selected: _
		})
	},
	getSelecteds: function() {
		return this.YkMq.findItems(this.value)
	},
	getSelected: function() {
		return this.getSelecteds()[0]
	},
	VsW: function(C) {
		var B = this.YkMq.getValue(),
			A = this.YkMq.B5fK(B),
			$ = this.getValue();
		this[UD7](B);
		this[Chg](A[1]);
		if($ != this.getValue()) {
			var _ = this;
			setTimeout(function() {
				_.PV2()
			}, 1)
		}
		if(!this[V0]) this[OsRe]();
		this.focus()
	},
	KPW: function(B) {
		this.fire("keydown", {
			htmlEvent: B
		});
		if(B.keyCode == 8 && (this[RBE]() || this.allowInput == false)) return false;
		if(B.keyCode == 9) {
			this[OsRe]();
			return
		}
		switch(B.keyCode) {
		case 27:
			if(this.isShowPopup()) B.stopPropagation();
			this[OsRe]();
			break;
		case 13:
			if(this.isShowPopup()) {
				B.preventDefault();
				B.stopPropagation();
				var _ = this.YkMq.getFocusedIndex();
				if(_ != -1) {
					var $ = this.YkMq.getAt(_),
						A = this.YkMq.B5fK([$]);
					this[UD7](A[0]);
					this[Chg](A[1]);
					this.PV2();
					this[OsRe]()
				}
			} else this.fire("enter");
			break;
		case 37:
			break;
		case 38:
			_ = this.YkMq.getFocusedIndex();
			if(_ == -1) {
				_ = 0;
				if(!this[V0]) {
					$ = this.YkMq.findItems(this.value)[0];
					if($) _ = this.YkMq.indexOf($)
				}
			}
			if(this.isShowPopup()) if(!this[V0]) {
				_ -= 1;
				if(_ < 0) _ = 0;
				this.YkMq.Kro(_, true)
			}
			break;
		case 39:
			break;
		case 40:
			_ = this.YkMq.getFocusedIndex();
			if(_ == -1) {
				_ = 0;
				if(!this[V0]) {
					$ = this.YkMq.findItems(this.value)[0];
					if($) _ = this.YkMq.indexOf($)
				}
			}
			if(this.isShowPopup()) {
				if(!this[V0]) {
					_ += 1;
					if(_ > this.YkMq.getCount() - 1) _ = this.YkMq.getCount() - 1;
					this.YkMq.Kro(_, true)
				}
			} else {
				this[Zpt]();
				if(!this[V0]) this.YkMq.Kro(_, true)
			}
			break;
		default:
			this._e8(this.IJ6.value);
			break
		}
	},
	KMzd: function($) {
		this.fire("keyup", {
			htmlEvent: $
		})
	},
	TG41: function($) {
		this.fire("keypress", {
			htmlEvent: $
		})
	},
	_e8: function(_) {
		var $ = this;
		setTimeout(function() {
			var A = $.IJ6.value;
			if(A != _) $.MyRg(A)
		}, 10)
	},
	MyRg: function(A) {
		var _ = [];
		for(var B = 0, D = this.data.length; B < D; B++) {
			var $ = this.data[B],
				C = $[this.textField];
			if(typeof C == "string") if(C.indexOf(A) != -1) _.push($)
		}
		this.YkMq[OUQ](_);
		this._filtered = true;
		if(A !== "" || this.isShowPopup()) this[Zpt]()
	},
	hidePopup: function() {
		var $ = this.getPopup();
		$.close();
		if(this._filtered) {
			this._filtered = false;
			if(this.YkMq.el) this.YkMq[OUQ](this.data)
		}
	},
	SDs: function($) {
		if(this[V0]);
		else if(this.IJ6.value == "" && !this.value) {
			this[UD7]("");
			this.PV2()
		}
	},
	getAttrs: function(G) {
		var E = ZjG[GR_][$gN][GkN](this, G);
		mini[ENl](G, E, ["url", "data", "textField", "valueField", "displayField"]);
		mini[XD9s](G, E, ["multiSelect", "showNullItem"]);
		if(E.displayField) E[LOda] = E.displayField;
		var C = E[B$Gk] || this[B$Gk],
			H = E[LOda] || this[LOda];
		if(G.nodeName.toLowerCase() == "select") {
			var I = [];
			for(var F = 0, D = G.length; F < D; F++) {
				var $ = G.options[F],
					_ = {};
				_[H] = $.text;
				_[C] = $.value;
				I.push(_)
			}
			if(I.length > 0) E.data = I
		} else {
			var J = mini[OIAh](G);
			for(F = 0, D = J.length; F < D; F++) {
				var A = J[F],
					B = jQuery(A).attr("property");
				if(!B) continue;
				B = B.toLowerCase();
				if(B == "columns") E.columns = mini._ParseColumns(A);
				else if(B == "data") E.data = A.innerHTML
			}
		}
		return E
	}
});
PC7(ZjG, "combobox");
KG = function() {
	KG[GR_][KNT][GkN](this)
};
Iov(KG, AU6v, {
	format: "yyyy-MM-dd",
	popupWidth: "",
	viewDate: new Date(),
	showTime: false,
	timeFormat: "H:mm",
	showTodayButton: true,
	showClearButton: true,
	uiCls: "mini-datepicker",
	_getCalendar: function() {
		if(!KG._Calendar) {
			var $ = KG._Calendar = new _8$e();
			$.setStyle("border:0;")
		}
		return KG._Calendar
	},
	GP8v: function() {
		KG[GR_].GP8v[GkN](this);
		this.Yss = this._getCalendar()
	},
	showPopup: function() {
		this.Yss.beginUpdate();
		this.Yss[Ivp](this.popup._contentEl);
		this.Yss.set({
			showTime: this.showTime,
			timeFormat: this.timeFormat,
			showClearButton: this.showClearButton,
			showTodayButton: this.showTodayButton
		});
		this.Yss[UD7](this.value);
		if(this.value) this.Yss.setViewDate(this.value);
		else this.Yss.setViewDate(this.viewDate);
		if(this.Yss._target) {
			var $ = this.Yss._target;
			this.Yss.un("timechanged", $._1UD, $);
			this.Yss.un("dateclick", $.EP, $);
			this.Yss.un("drawdate", $.OS, $)
		}
		this.Yss.on("timechanged", this._1UD, this);
		this.Yss.on("dateclick", this.EP, this);
		this.Yss.on("drawdate", this.OS, this);
		this.Yss.endUpdate();
		KG[GR_][Zpt][GkN](this);
		this.Yss._target = this;
		this.Yss.focus()
	},
	hidePopup: function() {
		KG[GR_][OsRe][GkN](this);
		this.Yss.un("timechanged", this._1UD, this);
		this.Yss.un("dateclick", this.EP, this);
		this.Yss.un("drawdate", this.OS, this)
	},
	within: function($) {
		if(TWAc(this.el, $.target)) return true;
		if(this.Yss[LU]($)) return true;
		return false
	},
	__OnPopupKeyDown: function($) {
		if($.keyCode == 13) this.EP();
		if($.keyCode == 27) {
			this[OsRe]();
			this.focus()
		}
	},
	OS: function($) {
		this.fire("drawdate", $)
	},
	EP: function(A) {
		var _ = this.Yss.getValue(),
			$ = this.getFormValue();
		this[UD7](_);
		if($ !== this.getFormValue()) this.PV2();
		this.focus();
		this[OsRe]()
	},
	_1UD: function(_) {
		var $ = this.Yss.getValue();
		this[UD7]($);
		this.PV2()
	},
	setFormat: function($) {
		if(typeof $ != "string") return;
		if(this.format != $) {
			this.format = $;
			this.IJ6.value = this.HRA.value = this.getFormValue()
		}
	},
	setValue: function($) {
		$ = mini.parseDate($);
		if(mini.isNull($)) $ = "";
		if(this.value != $) {
			this.value = $;
			this.IJ6.value = this.HRA.value = this.getFormValue()
		}
	},
	getValue: function() {
		if(!mini.isDate(this.value)) return null;
		return this.value
	},
	getFormValue: function() {
		if(!mini.isDate(this.value)) return "";
		return mini.formatDate(this.value, this.format)
	},
	setViewDate: function($) {
		$ = mini.parseDate($);
		if(!mini.isDate($)) return;
		this.viewDate = $
	},
	getViewDate: function() {
		return this.Yss.getViewDate()
	},
	setShowTime: function($) {
		if(this.showTime != $) this.showTime = $
	},
	getShowTime: function() {
		return this.showTime
	},
	setTimeFormat: function($) {
		if(this.timeFormat != $) this.timeFormat = $
	},
	getTimeFormat: function() {
		return this.timeFormat
	},
	setShowTodayButton: function($) {
		this.showTodayButton = $
	},
	getShowTodayButton: function() {
		return this.showTodayButton
	},
	setShowClearButton: function($) {
		this.showClearButton = $
	},
	getShowClearButton: function() {
		return this.showClearButton
	},
	SDs: function(B) {
		var A = this.IJ6.value,
			$ = mini.parseDate(A);
		if(!$ || isNaN($) || $.getFullYear() == 1970) $ = null;
		var _ = this.getFormValue();
		this[UD7]($);
		if($ == null) this.IJ6.value = "";
		if(_ !== this.getFormValue()) this.PV2()
	},
	KPW: function(_) {
		this.fire("keydown", {
			htmlEvent: _
		});
		if(_.keyCode == 8 && (this[RBE]() || this.allowInput == false)) return false;
		if(_.keyCode == 9) {
			this[OsRe]();
			return
		}
		switch(_.keyCode) {
		case 27:
			if(this.isShowPopup()) _.stopPropagation();
			this[OsRe]();
			break;
		case 13:
			if(this.isShowPopup()) {
				_.preventDefault();
				_.stopPropagation();
				this[OsRe]()
			} else {
				this.SDs(null);
				var $ = this;
				setTimeout(function() {
					$.fire("enter")
				}, 10)
			}
			break;
		case 37:
			break;
		case 38:
			_.preventDefault();
			break;
		case 39:
			break;
		case 40:
			_.preventDefault();
			this[Zpt]();
			break;
		default:
			break
		}
	},
	getAttrs: function($) {
		var _ = KG[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["format", "viewDate", "timeFormat", "ondrawdate"]);
		mini[XD9s]($, _, ["showTime", "showTodayButton", "showClearButton"]);
		return _
	}
});
PC7(KG, "datepicker");
_8$e = function() {
	this.viewDate = new Date(), this.Hv0 = [];
	_8$e[GR_][KNT][GkN](this)
};
Iov(_8$e, FIa, {
	width: 220,
	height: 160,
	_clearBorder: false,
	viewDate: null,
	ZW9: "",
	Hv0: [],
	multiSelect: false,
	firstDayOfWeek: 0,
	todayText: "Today",
	clearText: "Clear",
	okText: "OK",
	cancelText: "Cancel",
	daysShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
	format: "MMM,yyyy",
	timeFormat: "H:mm",
	showTime: false,
	currentTime: false,
	rows: 1,
	columns: 1,
	headerCls: "",
	bodyCls: "",
	footerCls: "",
	Ds: "mini-calendar-today",
	A83: "mini-calendar-weekend",
	Yhi: "mini-calendar-othermonth",
	BZ8: "mini-calendar-selected",
	showHeader: true,
	showFooter: true,
	showWeekNumber: false,
	showDaysHeader: true,
	showMonthButtons: true,
	showYearButtons: true,
	showTodayButton: true,
	showClearButton: true,
	isWeekend: function(_) {
		var $ = _.getDay();
		return $ == 0 || $ == 6
	},
	getFirstDateOfMonth: function($) {
		var $ = new Date($.getFullYear(), $.getMonth(), 1);
		return mini.getWeekStartDate($, this.firstDayOfWeek)
	},
	getShortWeek: function($) {
		return this.daysShort[$]
	},
	uiCls: "mini-calendar",
	_create: function() {
		var C = "<tr style=\"width:100%;\"><td style=\"width:100%;\"></td></tr>";
		C += "<tr ><td><div class=\"mini-calendar-footer\">" + "<span style=\"display:inline-block;\"><input name=\"time\" class=\"mini-timespinner\" style=\"width:60px\" format=\"" + this.timeFormat + "\"/>" + "<span class=\"mini-calendar-footerSpace\"></span></span>" + "<span class=\"mini-calendar-tadayButton\">" + this.todayText + "</span>" + "<span class=\"mini-calendar-footerSpace\"></span>" + "<span class=\"mini-calendar-clearButton\">" + this.clearText + "</span>" + "<a href=\"#\" class=\"mini-calendar-focus\" style=\"position:absolute;left:-10px;top:-10px;width:0px;height:0px;outline:none\" hideFocus></a>" + "</div></td></tr>";
		var A = "<table class=\"mini-calendar\" cellpadding=\"0\" cellspacing=\"0\">" + C + "</table>",
			_ = document.createElement("div");
		_.innerHTML = A;
		this.el = _.firstChild;
		var $ = this.el.getElementsByTagName("tr"),
			B = this.el.getElementsByTagName("td");
		this.VHBX = B[0];
		this.Yj = mini.byClass("mini-calendar-footer", this.el);
		this.timeWrapEl = this.Yj.childNodes[0];
		this.todayButtonEl = this.Yj.childNodes[1];
		this.footerSpaceEl = this.Yj.childNodes[2];
		this.closeButtonEl = this.Yj.childNodes[3];
		this._focusEl = this.Yj.lastChild;
		mini.parse(this.Yj);
		this.timeSpinner = mini.getbyName("time", this.el);
		this[Mdk]()
	},
	focus: function() {
		try {
			this._focusEl.focus()
		} catch($) {}
	},
	destroy: function($) {
		this.VHBX = this.Yj = this.timeWrapEl = this.todayButtonEl = this.footerSpaceEl = this.closeButtonEl = null;
		_8$e[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		if(this.timeSpinner) this.timeSpinner.on("valuechanged", this._1UD, this);
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this);
			$v9(this.el, "mousedown", this.AOlf, this);
			$v9(this.el, "keydown", this.HtB, this)
		}, this)
	},
	getDateEl: function($) {
		if(!$) return null;
		var _ = this.uid + "$" + mini.clearTime($).getTime();
		return document.getElementById(_)
	},
	within: function($) {
		if(TWAc(this.el, $.target)) return true;
		if(this.menuEl && TWAc(this.menuEl, $.target)) return true;
		return false
	},
	setShowClearButton: function($) {
		this.showClearButton = $;
		var _ = this.getButton("clear");
		if(_) this[Mdk]()
	},
	getShowClearButton: function() {
		return this.showClearButton
	},
	setShowHeader: function($) {
		this.showHeader = $;
		this[Mdk]()
	},
	getShowHeader: function() {
		return this.showHeader
	},
	setShowFooter: function($) {
		this[B8] = $;
		this[Mdk]()
	},
	getShowFooter: function() {
		return this[B8]
	},
	setShowWeekNumber: function($) {
		this.showWeekNumber = $;
		this[Mdk]()
	},
	getShowWeekNumber: function() {
		return this.showWeekNumber
	},
	setShowDaysHeader: function($) {
		this.showDaysHeader = $;
		this[Mdk]()
	},
	getShowDaysHeader: function() {
		return this.showDaysHeader
	},
	setShowMonthButtons: function($) {
		this.showMonthButtons = $;
		this[Mdk]()
	},
	getShowMonthButtons: function() {
		return this.showMonthButtons
	},
	setShowYearButtons: function($) {
		this.showYearButtons = $;
		this[Mdk]()
	},
	getShowYearButtons: function() {
		return this.showYearButtons
	},
	setShowTodayButton: function($) {
		this.showTodayButton = $;
		this[Mdk]()
	},
	getShowTodayButton: function() {
		return this.showTodayButton
	},
	setShowClearButton: function($) {
		this.showClearButton = $;
		this[Mdk]()
	},
	getShowClearButton: function() {
		return this.showClearButton
	},
	setViewDate: function($) {
		if(!$) $ = new Date();
		this.viewDate = $;
		this[Mdk]()
	},
	getViewDate: function() {
		return this.viewDate
	},
	setSelectedDate: function($) {
		$ = mini.parseDate($);
		if(!mini.isDate($)) $ = "";
		else $ = new Date($.getTime());
		var _ = this.getDateEl(this.ZW9);
		if(_) HMT(_, this.BZ8);
		this.ZW9 = $;
		if(this.ZW9) this.ZW9 = mini.cloneDate(this.ZW9);
		_ = this.getDateEl(this.ZW9);
		if(_) V7(_, this.BZ8);
		this.fire("datechanged")
	},
	setSelectedDates: function($) {
		if(!mini.isArray($)) $ = [];
		this.Hv0 = $;
		this[Mdk]()
	},
	getSelectedDate: function() {
		return this.ZW9 ? this.ZW9 : ""
	},
	setTime: function($) {
		this.timeSpinner[UD7]($)
	},
	getTime: function() {
		return this.timeSpinner.getFormValue()
	},
	setValue: function($) {
		this.setSelectedDate($);
		this.setTime($)
	},
	getValue: function() {
		var $ = this.ZW9;
		if($) {
			$ = mini.clearTime($);
			if(this.showTime) {
				var _ = this.timeSpinner.getValue();
				$.setHours(_.getHours());
				$.setMinutes(_.getMinutes());
				$.setSeconds(_.getSeconds())
			}
		}
		return $ ? $ : ""
	},
	getFormValue: function() {
		var $ = this.getValue();
		if($) return mini.formatDate($, "yyyy-MM-dd HH:mm:ss");
		return ""
	},
	isSelectedDate: function($) {
		if(!$ || !this.ZW9) return false;
		return mini.clearTime($).getTime() == mini.clearTime(this.ZW9).getTime()
	},
	setMultiSelect: function($) {
		this[V0] = $;
		this[Mdk]()
	},
	getMultiSelect: function() {
		return this[V0]
	},
	setRows: function($) {
		if(isNaN($)) return;
		if($ < 1) $ = 1;
		this.rows = $;
		this[Mdk]()
	},
	getRows: function() {
		return this.rows
	},
	setColumns: function($) {
		if(isNaN($)) return;
		if($ < 1) $ = 1;
		this.columns = $;
		this[Mdk]()
	},
	getColumns: function() {
		return this.columns
	},
	setShowTime: function($) {
		if(this.showTime != $) {
			this.showTime = $;
			this[Fcv]()
		}
	},
	getShowTime: function() {
		return this.showTime
	},
	setTimeFormat: function($) {
		if(this.timeFormat != $) {
			this.timeSpinner.setFormat($);
			this.timeFormat = this.timeSpinner.format
		}
	},
	getTimeFormat: function() {
		return this.timeFormat
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		this.timeWrapEl.style.display = this.showTime ? "" : "none";
		this.todayButtonEl.style.display = this.showTodayButton ? "" : "none";
		this.closeButtonEl.style.display = this.showClearButton ? "" : "none";
		this.footerSpaceEl.style.display = (this.showClearButton && this.showTodayButton) ? "" : "none";
		this.Yj.style.display = this[B8] ? "" : "none";
		var _ = this.VHBX.firstChild,
			$ = this[_H]();
		if(!$) {
			_.parentNode.style.height = "100px";
			h = jQuery(this.el).height();
			h -= jQuery(this.Yj).outerHeight();
			_.parentNode.style.height = h + "px"
		} else _.parentNode.style.height = "";
		mini.layout(this.Yj)
	},
	doUpdate: function() {
		if(!this.Y5o) return;
		var F = new Date(this.viewDate.getTime()),
			A = this.rows == 1 && this.columns == 1,
			B = 100 / this.rows,
			E = "<table class=\"mini-calendar-views\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
		for(var $ = 0, D = this.rows; $ < D; $++) {
			E += "<tr >";
			for(var C = 0, _ = this.columns; C < _; C++) {
				E += "<td style=\"height:" + B + "%\">";
				E += this.D8c(F, $, C);
				E += "</td>";
				F = new Date(F.getFullYear(), F.getMonth() + 1, 1)
			}
			E += "</tr>"
		}
		E += "</table>";
		this.VHBX.innerHTML = E;
		mini[CX7](this.el);
		this[Fcv]()
	},
	D8c: function(R, J, C) {
		var _ = R.getMonth(),
			F = this.getFirstDateOfMonth(R),
			K = new Date(F.getTime()),
			A = mini.clearTime(new Date()).getTime(),
			D = this.value ? mini.clearTime(this.value).getTime() : -1,
			N = this.rows > 1 || this.columns > 1,
			P = "";
		P += "<table class=\"mini-calendar-view\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
		if(this.showHeader) {
			P += "<tr ><td colSpan=\"10\" class=\"mini-calendar-header\"><div class=\"mini-calendar-headerInner\">";
			if(J == 0 && C == 0) {
				P += "<div class=\"mini-calendar-prev\">";
				if(this.showYearButtons) P += "<span class=\"mini-calendar-yearPrev\"></span>";
				if(this.showMonthButtons) P += "<span class=\"mini-calendar-monthPrev\"></span>";
				P += "</div>"
			}
			if(J == 0 && C == this.columns - 1) {
				P += "<div class=\"mini-calendar-next\">";
				if(this.showMonthButtons) P += "<span class=\"mini-calendar-monthNext\"></span>";
				if(this.showYearButtons) P += "<span class=\"mini-calendar-yearNext\"></span>";
				P += "</div>"
			}
			P += "<span class=\"mini-calendar-title\">" + mini.formatDate(R, this.format); + "</span>";
			P += "</div></td></tr>"
		}
		P += "<tr class=\"mini-calendar-daysheader\"><td class=\"mini-calendar-space\"></td>";
		if(this.showWeekNumber) P += "<td sclass=\"mini-calendar-weeknumber\"></td>";
		for(var L = this.firstDayOfWeek, B = L + 7; L < B; L++) {
			var O = this.getShortWeek(L);
			P += "<td valign=\"middle\">";
			P += O;
			P += "</td>";
			F = new Date(F.getFullYear(), F.getMonth(), F.getDate() + 1)
		}
		P += "<td class=\"mini-calendar-space\"></td></tr>";
		F = K;
		for(var H = 0; H <= 5; H++) {
			P += "<tr class=\"mini-calendar-days\"><td class=\"mini-calendar-space\"></td>";
			if(this.showWeekNumber) {
				var G = mini.getWeek(F.getFullYear(), F.getMonth() + 1, F.getDate());
				if(String(G).length == 1) G = "0" + G;
				P += "<td class=\"mini-calendar-weeknumber\" valign=\"middle\">" + G + "</td>"
			}
			for(L = this.firstDayOfWeek, B = L + 7; L < B; L++) {
				var M = this.isWeekend(F),
					I = mini.clearTime(F).getTime(),
					$ = I == A,
					E = this.isSelectedDate(F);
				if(_ != F.getMonth() && N) I = -1;
				var Q = this.YLd3(F);
				P += "<td valign=\"middle\" id=\"";
				P += this.uid + "$" + I;
				P += "\" class=\"mini-calendar-date ";
				if(M) P += " mini-calendar-weekend ";
				if(Q[MHU] == false) P += " mini-calendar-disabled ";
				if(_ != F.getMonth() && N);
				else {
					if(E) P += " " + this.BZ8 + " ";
					if($) P += " mini-calendar-today "
				}
				if(_ != F.getMonth()) P += " mini-calendar-othermonth ";
				P += "\">";
				if(_ != F.getMonth() && N);
				else P += Q.dateHtml;
				P += "</td>";
				F = new Date(F.getFullYear(), F.getMonth(), F.getDate() + 1)
			}
			P += "<td class=\"mini-calendar-space\"></td></tr>"
		}
		P += "<tr class=\"mini-calendar-bottom\" colSpan=\"10\"><td ></td></tr>";
		P += "</table>";
		return P
	},
	YLd3: function($) {
		var _ = {
			date: $,
			dateCls: "",
			dateStyle: "",
			dateHtml: $.getDate(),
			allowSelect: true
		};
		this.fire("drawdate", _);
		return _
	},
	Znb: function(_, $) {
		var A = {
			date: _,
			action: $
		};
		this.fire("dateclick", A);
		this.PV2()
	},
	menuEl: null,
	menuYear: null,
	menuSelectMonth: null,
	menuSelectYear: null,
	showMenu: function(_) {
		if(!_) return;
		this.hideMenu();
		this.menuYear = parseInt(this.viewDate.getFullYear() / 10) * 10;
		this.ClgelectMonth = this.viewDate.getMonth();
		this.ClgelectYear = this.viewDate.getFullYear();
		var A = "<div class=\"mini-calendar-menu\"></div>";
		this.menuEl = mini.append(document.body, A);
		this.updateMenu(this.viewDate);
		var $ = this.getBox();
		if(this.el.style.borderWidth == "0px") this.menuEl.style.border = "0";
		$vA(this.menuEl, $);
		$v9(this.menuEl, "click", this.MOK, this);
		$v9(document, "mousedown", this.Lf_u, this)
	},
	hideMenu: function() {
		if(this.menuEl) {
			M4(this.menuEl, "click", this.MOK, this);
			M4(document, "mousedown", this.Lf_u, this);
			jQuery(this.menuEl).remove();
			this.menuEl = null
		}
	},
	updateMenu: function() {
		var C = "<div class=\"mini-calendar-menu-months\">";
		for(var $ = 0, B = 12; $ < B; $++) {
			var _ = mini.getShortMonth($),
				A = "";
			if(this.ClgelectMonth == $) A = "mini-calendar-menu-selected";
			C += "<a id=\"" + $ + "\" class=\"mini-calendar-menu-month " + A + "\" href=\"javascript:void(0);\" hideFocus onclick=\"return false\">" + _ + "</a>"
		}
		C += "<div style=\"clear:both;\"></div></div>";
		C += "<div class=\"mini-calendar-menu-years\">";
		for($ = this.menuYear, B = this.menuYear + 10; $ < B; $++) {
			_ = $, A = "";
			if(this.ClgelectYear == $) A = "mini-calendar-menu-selected";
			C += "<a id=\"" + $ + "\" class=\"mini-calendar-menu-year " + A + "\" href=\"javascript:void(0);\" hideFocus onclick=\"return false\">" + _ + "</a>"
		}
		C += "<div class=\"mini-calendar-menu-prevYear\"></div><div class=\"mini-calendar-menu-nextYear\"></div><div style=\"clear:both;\"></div></div>";
		C += "<div class=\"mini-calendar-footer\">" + "<span class=\"mini-calendar-okButton\">" + this.okText + "</span>" + "<span class=\"mini-calendar-footerSpace\"></span>" + "<span class=\"mini-calendar-cancelButton\">" + this.cancelText + "</span>" + "</div><div style=\"clear:both;\"></div>";
		this.menuEl.innerHTML = C
	},
	MOK: function(C) {
		var _ = C.target,
			B = ZW(_, "mini-calendar-menu-month"),
			$ = ZW(_, "mini-calendar-menu-year");
		if(B) {
			this.ClgelectMonth = parseInt(B.id);
			this.updateMenu()
		} else if($) {
			this.ClgelectYear = parseInt($.id);
			this.updateMenu()
		} else if(ZW(_, "mini-calendar-menu-prevYear")) {
			this.menuYear = this.menuYear - 1;
			this.menuYear = parseInt(this.menuYear / 10) * 10;
			this.updateMenu()
		} else if(ZW(_, "mini-calendar-menu-nextYear")) {
			this.menuYear = this.menuYear + 11;
			this.menuYear = parseInt(this.menuYear / 10) * 10;
			this.updateMenu()
		} else if(ZW(_, "mini-calendar-okButton")) {
			var A = new Date(this.ClgelectYear, this.ClgelectMonth, 1);
			this.setViewDate(A);
			this.hideMenu()
		} else if(ZW(_, "mini-calendar-cancelButton")) this.hideMenu()
	},
	Lf_u: function($) {
		if(!ZW($.target, "mini-calendar-menu")) this.hideMenu()
	},
	PGY: function(H) {
		var G = this.viewDate;
		if(this.enabled == false) return;
		var C = H.target,
			F = ZW(H.target, "mini-calendar-title");
		if(ZW(C, "mini-calendar-monthNext")) {
			G.setMonth(G.getMonth() + 1);
			this.setViewDate(G)
		} else if(ZW(C, "mini-calendar-yearNext")) {
			G.setFullYear(G.getFullYear() + 1);
			this.setViewDate(G)
		} else if(ZW(C, "mini-calendar-monthPrev")) {
			G.setMonth(G.getMonth() - 1);
			this.setViewDate(G)
		} else if(ZW(C, "mini-calendar-yearPrev")) {
			G.setFullYear(G.getFullYear() - 1);
			this.setViewDate(G)
		} else if(ZW(C, "mini-calendar-tadayButton")) {
			var _ = mini.clearTime(new Date());
			this.setViewDate(_);
			this.setSelectedDate(_);
			if(this.currentTime) {
				var $ = new Date();
				this.setTime($)
			}
			this.Znb(_, "today")
		} else if(ZW(C, "mini-calendar-clearButton")) {
			this.setSelectedDate(null);
			this.setTime(null);
			this.Znb(null, "clear")
		} else if(F) this.showMenu(F);
		var E = ZW(H.target, "mini-calendar-date");
		if(E && !XPZ(E, "mini-calendar-disabled")) {
			var A = E.id.split("$"),
				B = parseInt(A[A.length - 1]);
			if(B == -1) return;
			var D = new Date(B);
			this.Znb(D)
		}
	},
	AOlf: function(C) {
		if(this.enabled == false) return;
		var B = ZW(C.target, "mini-calendar-date");
		if(B && !XPZ(B, "mini-calendar-disabled")) {
			var $ = B.id.split("$"),
				_ = parseInt($[$.length - 1]);
			if(_ == -1) return;
			var A = new Date(_);
			this.setSelectedDate(A)
		}
	},
	_1UD: function($) {
		this.fire("timechanged");
		this.PV2()
	},
	HtB: function(B) {
		if(this.enabled == false) return;
		var _ = this.getSelectedDate();
		if(!_) _ = new Date(this.viewDate.getTime());
		switch(B.keyCode) {
		case 27:
			break;
		case 13:
			break;
		case 37:
			_ = mini.addDate(_, -1, "D");
			break;
		case 38:
			_ = mini.addDate(_, -7, "D");
			break;
		case 39:
			_ = mini.addDate(_, 1, "D");
			break;
		case 40:
			_ = mini.addDate(_, 7, "D");
			break;
		default:
			break
		}
		var $ = this;
		if(_.getMonth() != $.viewDate.getMonth()) {
			$.setViewDate(mini.cloneDate(_));
			$.focus()
		}
		var A = this.getDateEl(_);
		if(A && XPZ(A, "mini-calendar-disabled")) return;
		$.setSelectedDate(_);
		if(B.keyCode == 37 || B.keyCode == 38 || B.keyCode == 39 || B.keyCode == 40) B.preventDefault()
	},
	PV2: function() {
		this.fire("valuechanged")
	},
	getAttrs: function($) {
		var _ = _8$e[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["viewDate", "rows", "columns", "ondateclick", "ondrawdate", "ondatechanged", "timeFormat", "ontimechanged", "onvaluechanged"]);
		mini[XD9s]($, _, ["multiSelect", "showHeader", "showFooter", "showWeekNumber", "showDaysHeader", "showMonthButtons", "showYearButtons", "showTodayButton", "showClearButton", "showTime"]);
		return _
	}
});
PC7(_8$e, "calendar");
YTBL = function() {
	YTBL[GR_][KNT][GkN](this)
};
Iov(YTBL, GR, {
	formField: true,
	width: 200,
	columns: null,
	columnWidth: 80,
	showNullItem: false,
	nullText: "",
	showEmpty: false,
	emptyText: "",
	showCheckBox: false,
	showAllCheckBox: true,
	multiSelect: false,
	GM_: "mini-listbox-item",
	OSK: "mini-listbox-item-hover",
	_O2q: "mini-listbox-item-selected",
	uiCls: "mini-listbox",
	_create: function() {
		var $ = this.el = document.createElement("div");
		this.el.className = "mini-listbox";
		this.el.innerHTML = "<div class=\"mini-listbox-border\"><div class=\"mini-listbox-header\"></div><div class=\"mini-listbox-view\"></div><input type=\"hidden\"/></div><div class=\"mini-errorIcon\"></div>";
		this.TG = this.el.firstChild;
		this.NL = this.TG.firstChild;
		this.ZVh = this.TG.childNodes[1];
		this.HRA = this.TG.childNodes[2];
		this.Izgy = this.el.lastChild;
		this.XbE = this.ZVh
	},
	destroy: function($) {
		if(this.ZVh) {
			mini[J9](this.ZVh);
			this.ZVh = null
		}
		this.TG = null;
		this.NL = null;
		this.ZVh = null;
		this.HRA = null;
		YTBL[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		YTBL[GR_][S1B][GkN](this);
		IZY2(function() {
			MT(this.ZVh, "scroll", this.Zmc, this)
		}, this)
	},
	destroy: function($) {
		if(this.ZVh) this.ZVh.onscroll = null;
		YTBL[GR_][EqU5][GkN](this, $)
	},
	setColumns: function(_) {
		if(!mini.isArray(_)) _ = [];
		this.columns = _;
		for(var $ = 0, D = this.columns.length; $ < D; $++) {
			var B = this.columns[$];
			if(B.type) {
				if(!mini.isNull(B.header) && typeof B.header !== "function") if(B.header.trim() == "") delete B.header;
				var C = mini[S56](B.type);
				if(C) {
					var E = mini.copyTo({}, B);
					mini.copyTo(B, C);
					mini.copyTo(B, E)
				}
			}
			var A = parseInt(B.width);
			if(mini.isNumber(A) && String(A) == B.width) B.width = A + "px";
			if(mini.isNull(B.width)) B.width = this[DzN] + "px"
		}
		this[Mdk]()
	},
	getColumns: function() {
		return this.columns
	},
	doUpdate: function() {
		if(this.Y5o === false) return;
		var S = this.columns && this.columns.length > 0;
		if(S) V7(this.el, "mini-listbox-showColumns");
		else HMT(this.el, "mini-listbox-showColumns");
		this.NL.style.display = S ? "" : "none";
		var I = [];
		if(S) {
			I[I.length] = "<table class=\"mini-listbox-headerInner\" cellspacing=\"0\" cellpadding=\"0\"><tr>";
			var D = this.uid + "$ck$all";
			I[I.length] = "<td class=\"mini-listbox-checkbox\"><input type=\"checkbox\" id=\"" + D + "\"></td>";
			for(var R = 0, _ = this.columns.length; R < _; R++) {
				var B = this.columns[R],
					E = B.header;
				if(mini.isNull(E)) E = "&nbsp;";
				var A = B.width;
				if(mini.isNumber(A)) A = A + "px";
				I[I.length] = "<td class=\"";
				if(B.headerCls) I[I.length] = B.headerCls;
				I[I.length] = "\" style=\"";
				if(B.headerStyle) I[I.length] = B.headerStyle + ";";
				if(A) I[I.length] = "width:" + A + ";";
				if(B.headerAlign) I[I.length] = "text-align:" + B.headerAlign + ";";
				I[I.length] = "\">";
				I[I.length] = E;
				I[I.length] = "</td>"
			}
			I[I.length] = "</tr></table>"
		}
		this.NL.innerHTML = I.join("");
		var I = [],
			P = this.data;
		I[I.length] = "<table class=\"mini-listbox-items\" cellspacing=\"0\" cellpadding=\"0\">";
		if(this[PN] && P.length == 0) I[I.length] = "<tr><td colspan=\"20\">" + this[QGN] + "</td></tr>";
		else {
			this.GtZX();
			for(var K = 0, G = P.length; K < G; K++) {
				var $ = P[K],
					M = -1,
					O = " ",
					J = -1,
					N = " ";
				I[I.length] = "<tr id=\"";
				I[I.length] = this.A_iN(K);
				I[I.length] = "\" index=\"";
				I[I.length] = K;
				I[I.length] = "\" class=\"mini-listbox-item ";
				if($.enabled === false) I[I.length] = " mini-disabled ";
				M = I.length;
				I[I.length] = O;
				I[I.length] = "\" style=\"";
				J = I.length;
				I[I.length] = N;
				I[I.length] = "\">";
				var H = this.Sd(K),
					L = this.name,
					F = this[Ez9]($),
					C = "";
				if($.enabled === false) C = "disabled";
				I[I.length] = "<td class=\"mini-listbox-checkbox\"><input " + C + " id=\"" + H + "\" type=\"checkbox\" ></td>";
				if(S) {
					for(R = 0, _ = this.columns.length; R < _; R++) {
						var B = this.columns[R],
							T = this.GgLg($, K, B),
							A = B.width;
						if(typeof A == "number") A = A + "px";
						I[I.length] = "<td class=\"";
						if(T.cellCls) I[I.length] = T.cellCls;
						I[I.length] = "\" style=\"";
						if(T.cellStyle) I[I.length] = T.cellStyle + ";";
						if(A) I[I.length] = "width:" + A + ";";
						if(B.align) I[I.length] = "text-align:" + B.align + ";";
						I[I.length] = "\">";
						I[I.length] = T.cellHtml;
						I[I.length] = "</td>";
						if(T.rowCls) O = T.rowCls;
						if(T.rowStyle) N = T.rowStyle
					}
				} else {
					T = this.GgLg($, K, null);
					I[I.length] = "<td class=\"";
					if(T.cellCls) I[I.length] = T.cellCls;
					I[I.length] = "\" style=\"";
					if(T.cellStyle) I[I.length] = T.cellStyle;
					I[I.length] = "\">";
					I[I.length] = T.cellHtml;
					I[I.length] = "</td>";
					if(T.rowCls) O = T.rowCls;
					if(T.rowStyle) N = T.rowStyle
				}
				I[M] = O;
				I[J] = N;
				I[I.length] = "</tr>"
			}
		}
		I[I.length] = "</table>";
		var Q = I.join("");
		this.ZVh.innerHTML = Q;
		this.Lbq();
		this[Fcv]()
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		if(this.columns && this.columns.length > 0) V7(this.el, "mini-listbox-showcolumns");
		else HMT(this.el, "mini-listbox-showcolumns");
		if(this[HbI]) HMT(this.el, "mini-listbox-hideCheckBox");
		else V7(this.el, "mini-listbox-hideCheckBox");
		var D = this.uid + "$ck$all",
			B = document.getElementById(D);
		if(B) B.style.display = this[Stg] ? "" : "none";
		var E = this[_H]();
		h = this[DTTy](true);
		_ = this[Y1Q](true);
		var C = _,
			F = this.ZVh;
		F.style.width = _ + "px";
		if(!E) {
			var $ = KwY(this.NL);
			h = h - $;
			F.style.height = h + "px"
		} else F.style.height = "auto";
		if(isIE) {
			var A = this.NL.firstChild,
				G = this.ZVh.firstChild;
			if(this.ZVh.offsetHeight >= this.ZVh.scrollHeight) {
				G.style.width = "100%";
				if(A) A.style.width = "100%"
			} else {
				var _ = parseInt(G.parentNode.offsetWidth - 17) + "px";
				G.style.width = _;
				if(A) A.style.width = _
			}
		}
		if(this.ZVh.offsetHeight < this.ZVh.scrollHeight) this.NL.style.width = (C - 17) + "px";
		else this.NL.style.width = "100%"
	},
	setShowCheckBox: function($) {
		this[HbI] = $;
		this[Fcv]()
	},
	getShowCheckBox: function() {
		return this[HbI]
	},
	setShowAllCheckBox: function($) {
		this[Stg] = $;
		this[Fcv]()
	},
	getShowAllCheckBox: function() {
		return this[Stg]
	},
	setShowNullItem: function($) {
		if(this.showNullItem != $) {
			this.showNullItem = $;
			this.GtZX();
			this[Mdk]()
		}
	},
	getShowNullItem: function() {
		return this.showNullItem
	},
	GtZX: function() {
		for(var _ = 0, A = this.data.length; _ < A; _++) {
			var $ = this.data[_];
			if($.__NullItem) {
				this.data.removeAt(_);
				break
			}
		}
		if(this.showNullItem) {
			$ = {
				__NullItem: true
			};
			$[this.textField] = this.nullText;
			$[this.valueField] = "";
			this.data.insert(0, $)
		}
	},
	removeAll: function() {
		var $ = this.getData();
		this.removeItems($)
	},
	addItems: function(_, $) {
		if(!mini.isArray(_)) return;
		if(mini.isNull($)) $ = this.data.length;
		this.data.insertRange($, _);
		this[Mdk]()
	},
	addItem: function(_, $) {
		if(!_) return;
		if(this.data.indexOf(_) != -1) return;
		if(mini.isNull($)) $ = this.data.length;
		this.data.insert($, _);
		this[Mdk]()
	},
	removeItems: function($) {
		if(!mini.isArray($)) return;
		this.data.removeRange($);
		this.B42d();
		this[Mdk]()
	},
	removeItem: function(_) {
		var $ = this.data.indexOf(_);
		if($ != -1) {
			this.data.removeAt($);
			this.B42d();
			this[Mdk]()
		}
	},
	moveItem: function(_, $) {
		if(!_ || !mini.isNumber($)) return;
		if($ < 0) $ = 0;
		if($ > this.data.length) $ = this.data.length;
		this.data.remove(_);
		this.data.insert($, _);
		this[Mdk]()
	},
	GgLg: function(_, $, C) {
		var A = C ? _[C.field] : this[Jach](_),
			D = {
				sender: this,
				index: $,
				rowIndex: $,
				record: _,
				item: _,
				column: C,
				field: C ? C.field : null,
				value: A,
				cellHtml: A,
				rowCls: null,
				cellCls: C ? (C.cellCls || "") : "",
				rowStyle: null,
				cellStyle: C ? (C.cellStyle || "") : ""
			};
		if(C) {
			if(C.dateFormat) if(mini.isDate(D.value)) D.cellHtml = mini.formatDate(A, C.dateFormat);
			else D.cellHtml = A;
			var B = C.renderer;
			if(B) {
				fn = typeof B == "function" ? B : window[B];
				if(fn) D.cellHtml = fn[GkN](C, D)
			}
		}
		this.fire("drawcell", D);
		if(D.cellHtml === null || D.cellHtml === undefined || D.cellHtml === "") D.cellHtml = "&nbsp;";
		return D
	},
	Zmc: function($) {
		this.NL.scrollLeft = this.ZVh.scrollLeft
	},
	PGY: function(C) {
		var A = this.uid + "$ck$all";
		if(C.target.id == A) {
			var _ = document.getElementById(A);
			if(_) {
				var B = _.checked,
					$ = this.getValue();
				this._CanFireSelectionChanged = true;
				if(B) this.selectAll();
				else this[KO2]();
				this._CanFireSelectionChanged = false;
				if($ != this.getValue()) {
					this.PV2();
					this.fire("itemclick", {
						htmlEvent: C
					})
				}
			}
			return
		}
		this.E4yM(C, "Click")
	},
	getAttrs: function(_) {
		var E = YTBL[GR_][$gN][GkN](this, _);
		mini[XD9s](_, E, ["showCheckBox", "showAllCheckBox", "showNullItem"]);
		if(_.nodeName.toLowerCase() != "select") {
			var C = mini[OIAh](_);
			for(var $ = 0, D = C.length; $ < D; $++) {
				var B = C[$],
					A = jQuery(B).attr("property");
				if(!A) continue;
				A = A.toLowerCase();
				if(A == "columns") E.columns = mini._ParseColumns(B);
				else if(A == "data") E.data = B.innerHTML
			}
		}
		return E
	}
});
PC7(YTBL, "listbox");
JcT = function() {
	JcT[GR_][KNT][GkN](this)
};
Iov(JcT, GR, {
	formField: true,
	multiSelect: true,
	repeatItems: 0,
	repeatLayout: "none",
	repeatDirection: "horizontal",
	GM_: "mini-checkboxlist-item",
	OSK: "mini-checkboxlist-item-hover",
	_O2q: "mini-checkboxlist-item-selected",
	VgsE: "mini-checkboxlist-table",
	MIT: "mini-checkboxlist-td",
	Maj: "checkbox",
	uiCls: "mini-checkboxlist",
	_create: function() {
		var $ = this.el = document.createElement("div");
		this.el.className = this.uiCls;
		this.el.innerHTML = "<div class=\"mini-list-inner\"></div><div class=\"mini-errorIcon\"></div><input type=\"hidden\" />";
		this.VHBX = this.el.firstChild;
		this.HRA = this.el.lastChild;
		this.Izgy = this.el.childNodes[1]
	},
	Nh: function() {
		var B = [];
		if(this.repeatItems > 0) {
			if(this.repeatDirection == "horizontal") {
				var D = [];
				for(var C = 0, E = this.data.length; C < E; C++) {
					var A = this.data[C];
					if(D.length == this.repeatItems) {
						B.push(D);
						D = []
					}
					D.push(A)
				}
				B.push(D)
			} else {
				var _ = this.repeatItems > this.data.length ? this.data.length : this.repeatItems;
				for(C = 0, E = _; C < E; C++) B.push([]);
				for(C = 0, E = this.data.length; C < E; C++) {
					var A = this.data[C],
						$ = C % this.repeatItems;
					B[$].push(A)
				}
			}
		} else B = [this.data.clone()];
		return B
	},
	doUpdate: function() {
		var D = this.data,
			G = "";
		for(var A = 0, F = D.length; A < F; A++) {
			var _ = D[A];
			_._i = A
		}
		if(this.repeatLayout == "flow") {
			var $ = this.Nh();
			for(A = 0, F = $.length; A < F; A++) {
				var C = $[A];
				for(var E = 0, B = C.length; E < B; E++) {
					_ = C[E];
					G += this.TF(_, _._i)
				}
				if(A != F - 1) G += "<br/>"
			}
		} else if(this.repeatLayout == "table") {
			$ = this.Nh();
			G += "<table class=\"" + this.VgsE + "\" cellpadding=\"0\" cellspacing=\"1\">";
			for(A = 0, F = $.length; A < F; A++) {
				C = $[A];
				G += "<tr>";
				for(E = 0, B = C.length; E < B; E++) {
					_ = C[E];
					G += "<td class=\"" + this.MIT + "\">";
					G += this.TF(_, _._i);
					G += "</td>"
				}
				G += "</tr>"
			}
			G += "</table>"
		} else for(A = 0, F = D.length; A < F; A++) {
			_ = D[A];
			G += this.TF(_, A)
		}
		this.VHBX.innerHTML = G;
		for(A = 0, F = D.length; A < F; A++) {
			_ = D[A];
			delete _._i
		}
	},
	TF: function(_, $) {
		var F = this.Fn09(_, $),
			E = this.A_iN($),
			A = this.Sd($),
			C = this[Ez9](_),
			B = "",
			D = "<div id=\"" + E + "\" index=\"" + $ + "\" class=\"" + this.GM_ + " ";
		if(_.enabled === false) {
			D += " mini-disabled ";
			B = "disabled"
		}
		D += F.itemCls + "\" style=\"" + F.itemStyle + "\"><input " + B + " value=\"" + C + "\" id=\"" + A + "\" type=\"" + this.Maj + "\"/><label for=\"" + A + "\" onclick=\"return false;\">";
		D += F.itemHtml + "</label></div>";
		return D
	},
	Fn09: function(_, $) {
		var A = this[Jach](_),
			B = {
				index: $,
				item: _,
				itemHtml: A,
				itemCls: "",
				itemStyle: ""
			};
		this.fire("drawitem", B);
		if(B.itemHtml === null || B.itemHtml === undefined) B.itemHtml = "";
		return B
	},
	setRepeatItems: function($) {
		$ = parseInt($);
		if(isNaN($)) $ = 0;
		if(this.repeatItems != $) {
			this.repeatItems = $;
			this[Mdk]()
		}
	},
	getRepeatItems: function() {
		return this.repeatItems
	},
	setRepeatLayout: function($) {
		if($ != "flow" && $ != "table") $ = "none";
		if(this.repeatLayout != $) {
			this.repeatLayout = $;
			this[Mdk]()
		}
	},
	getRepeatLayout: function() {
		return this.repeatLayout
	},
	setRepeatDirection: function($) {
		if($ != "vertical") $ = "horizontal";
		if(this.repeatDirection != $) {
			this.repeatDirection = $;
			this[Mdk]()
		}
	},
	getRepeatDirection: function() {
		return this.repeatDirection
	},
	getAttrs: function(_) {
		var D = JcT[GR_][$gN][GkN](this, _),
			C = jQuery(_),
			$ = parseInt(C.attr("repeatItems"));
		if(!isNaN($)) D.repeatItems = $;
		var B = C.attr("repeatLayout");
		if(B) D.repeatLayout = B;
		var A = C.attr("repeatDirection");
		if(A) D.repeatDirection = A;
		return D
	}
});
PC7(JcT, "checkboxlist");
NjUP = function() {
	NjUP[GR_][KNT][GkN](this)
};
Iov(NjUP, JcT, {
	multiSelect: false,
	GM_: "mini-radiobuttonlist-item",
	OSK: "mini-radiobuttonlist-item-hover",
	_O2q: "mini-radiobuttonlist-item-selected",
	VgsE: "mini-radiobuttonlist-table",
	MIT: "mini-radiobuttonlist-td",
	Maj: "radio",
	uiCls: "mini-radiobuttonlist"
});
PC7(NjUP, "radiobuttonlist");
Zt0 = function() {
	this.data = [];
	Zt0[GR_][KNT][GkN](this)
};
Iov(Zt0, AU6v, {
	text: "",
	value: "",
	valueField: "id",
	textField: "text",
	delimiter: ",",
	multiSelect: false,
	data: [],
	url: "",
	allowInput: false,
	showTreeIcon: false,
	showTreeLines: true,
	resultAsTree: false,
	parentField: "pid",
	checkRecursive: false,
	showFolderCheckBox: false,
	popupWidth: 200,
	popupMaxHeight: 250,
	popupMinWidth: 100,
	set: function(B) {
		if(typeof B == "string") return this;
		var $ = B.value;
		delete B.value;
		var _ = B.text;
		delete B.text;
		var C = B.url;
		delete B.url;
		var A = B.data;
		delete B.data;
		Zt0[GR_].set[GkN](this, B);
		if(!mini.isNull(A)) this[OUQ](A);
		if(!mini.isNull(C)) this.setUrl(C);
		if(!mini.isNull($)) this[UD7]($);
		if(!mini.isNull(_)) this[Chg](_);
		return this
	},
	uiCls: "mini-treeselect",
	GP8v: function() {
		Zt0[GR_].GP8v[GkN](this);
		this.tree = new L2();
		this.tree.setShowTreeIcon(true);
		this.tree.setStyle("border:0;width:100%;height:100%;");
		this.tree.setResultAsTree(this[ATIK]);
		this.tree[Ivp](this.popup._contentEl);
		this.tree.on("nodeclick", this.HIv, this);
		this.tree.on("nodecheck", this.TCH, this);
		this.tree.on("expand", this.I3aT, this);
		this.tree.on("collapse", this.GDL, this);
		this.tree.on("beforenodecheck", this.Q$bE, this);
		this.tree.on("beforenodeselect", this.YXj, this);
		this.tree.allowAnim = false
	},
	Q$bE: function($) {
		$.tree = $.sender;
		this.fire("beforenodecheck", $)
	},
	YXj: function($) {
		$.tree = $.sender;
		this.fire("beforenodeselect", $)
	},
	I3aT: function($) {
		this[Zpt]()
	},
	GDL: function($) {
		this[Zpt]()
	},
	showPopup: function() {
		this.tree[P4]("auto");
		Zt0[GR_][Zpt][GkN](this);
		var $ = this.popup.el.style.height;
		if($ == "" || $ == "auto") this.tree[P4]("auto");
		else this.tree[P4]("100%");
		this.tree[UD7](this.value)
	},
	getItem: function($) {
		return typeof $ == "object" ? $ : this.data[$]
	},
	indexOf: function($) {
		return this.data.indexOf($)
	},
	getAt: function($) {
		return this.data[$]
	},
	load: function($) {
		this.tree.load($)
	},
	setData: function($) {
		this.tree[OUQ]($);
		this.data = this.tree.data
	},
	getData: function() {
		return this.data
	},
	setUrl: function($) {
		this.getPopup();
		this.tree.setUrl($);
		this.url = this.tree.url
	},
	getUrl: function() {
		return this.url
	},
	setTextField: function($) {
		if(this.tree) this.tree.setTextField($);
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setValue: function($) {
		if(this.value != $) {
			var _ = this.tree.B5fK($);
			this.value = $;
			this.HRA.value = $;
			this.IJ6.value = _[1];
			this.DwrN()
		}
	},
	setMultiSelect: function($) {
		if(this[V0] != $) {
			this[V0] = $;
			this.tree.setShowCheckBox($);
			this.tree.setAllowSelect(!$)
		}
	},
	getMultiSelect: function() {
		return this[V0]
	},
	HIv: function(B) {
		if(this[V0]) return;
		var _ = this.tree.getSelectedNode(),
			A = this.tree[Ez9](_),
			$ = this.getValue();
		this[UD7](A);
		if($ != this.getValue()) this.PV2();
		this[OsRe]()
	},
	TCH: function(A) {
		if(!this[V0]) return;
		var _ = this.tree.getValue(),
			$ = this.getValue();
		this[UD7](_);
		if($ != this.getValue()) this.PV2()
	},
	setCheckRecursive: function($) {
		this[PiyK] = $;
		if(this.tree) this.tree.setCheckRecursive($)
	},
	getCheckRecursive: function() {
		return this[PiyK]
	},
	setResultAsTree: function($) {
		this[ATIK] = $;
		if(this.tree) this.tree.setResultAsTree($)
	},
	getResultAsTree: function() {
		return this[ATIK]
	},
	setParentField: function($) {
		this[Rcs] = $;
		if(this.tree) this.tree.setParentField($)
	},
	getParentField: function() {
		return this[Rcs]
	},
	setValueField: function($) {
		if(this.tree) this.tree.setIdField($);
		this[B$Gk] = $
	},
	getValueField: function() {
		return this[B$Gk]
	},
	setShowTreeIcon: function($) {
		this[OvNg] = $;
		if(this.tree) this.tree.setShowTreeIcon($)
	},
	getShowTreeIcon: function() {
		return this[OvNg]
	},
	setShowTreeLines: function($) {
		this[Pi] = $;
		if(this.tree) this.tree.setShowTreeLines($)
	},
	getShowTreeLines: function() {
		return this[Pi]
	},
	setShowFolderCheckBox: function($) {
		this[F0q] = $;
		if(this.tree) this.tree.setShowFolderCheckBox($)
	},
	getShowFolderCheckBox: function() {
		return this[F0q]
	},
	getAttrs: function($) {
		var _ = ZjG[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["url", "data", "textField", "valueField", "parentField", "onbeforenodecheck", "onbeforenodeselect"]);
		mini[XD9s]($, _, ["multiSelect", "resultAsTree", "checkRecursive", "showTreeIcon", "showTreeLines", "showFolderCheckBox"]);
		return _
	}
});
PC7(Zt0, "TreeSelect");
HY84 = function() {
	HY84[GR_][KNT][GkN](this);
	this[UD7](this[Njfb])
};
Iov(HY84, OuY, {
	value: 0,
	minValue: 0,
	maxValue: 100,
	increment: 1,
	decimalPlaces: 0,
	set: function(_) {
		if(typeof _ == "string") return this;
		var $ = _.value;
		delete _.value;
		HY84[GR_].set[GkN](this, _);
		if(!mini.isNull($)) this[UD7]($);
		return this
	},
	uiCls: "mini-spinner",
	DpiHtml: function() {
		var $ = "onmouseover=\"V7(this,'" + this.ME + "');\" " + "onmouseout=\"HMT(this,'" + this.ME + "');\"";
		return "<span class=\"mini-buttonedit-button\" " + $ + "><span class=\"mini-buttonedit-up\"><span></span></span><span class=\"mini-buttonedit-down\"><span></span></span></span>"
	},
	_initEvents: function() {
		HY84[GR_][S1B][GkN](this);
		IZY2(function() {
			this.on("buttonmousedown", this.BvF, this);
			$v9(this.el, "mousewheel", this.W8, this)
		}, this)
	},
	PvcH: function() {
		if(this[Njfb] > this[PiF]) this[PiF] = this[Njfb] + 100;
		if(this.value < this[Njfb]) this[UD7](this[Njfb]);
		if(this.value > this[PiF]) this[UD7](this[PiF])
	},
	setValue: function($) {
		$ = parseFloat($);
		if(isNaN($)) $ = this[Njfb];
		$ = parseFloat($.toFixed(this[CkfO]));
		if(this.value != $) {
			this.value = $;
			this.PvcH();
			this.IJ6.value = this.HRA.value = this.getFormValue()
		} else this.IJ6.value = this.getFormValue()
	},
	setMaxValue: function($) {
		$ = parseFloat($);
		if(isNaN($)) return;
		$ = parseFloat($.toFixed(this[CkfO]));
		if(this[PiF] != $) {
			this[PiF] = $;
			this.PvcH()
		}
	},
	getMaxValue: function($) {
		return this[PiF]
	},
	setMinValue: function($) {
		$ = parseFloat($);
		if(isNaN($)) return;
		$ = parseFloat($.toFixed(this[CkfO]));
		if(this[Njfb] != $) {
			this[Njfb] = $;
			this.PvcH()
		}
	},
	getMinValue: function($) {
		return this[Njfb]
	},
	setIncrement: function($) {
		$ = parseFloat($);
		if(isNaN($)) return;
		if(this[NfR] != $) this[NfR] = $
	},
	getIncrement: function($) {
		return this[NfR]
	},
	setDecimalPlaces: function($) {
		$ = parseInt($);
		if(isNaN($) || $ < 0) return;
		this[CkfO] = $
	},
	getDecimalPlaces: function($) {
		return this[CkfO]
	},
	TtX: null,
	Xv: function(D, B, C) {
		this.MEK();
		this[UD7](this.value + D);
		this.PV2();
		var A = this,
			_ = C,
			$ = new Date();
		this.TtX = setInterval(function() {
			A[UD7](A.value + D);
			A.PV2();
			C--;
			if(C == 0 && B > 50) A.Xv(D, B - 100, _ + 3);
			var E = new Date();
			if(E - $ > 500) A.MEK();
			$ = E
		}, B);
		$v9(document, "mouseup", this.ZG47, this)
	},
	MEK: function() {
		clearInterval(this.TtX);
		this.TtX = null
	},
	BvF: function($) {
		this._DownValue = this.getFormValue();
		if($.spinType == "up") this.Xv(this.increment, 230, 2);
		else this.Xv(-this.increment, 230, 2)
	},
	HtB: function(_) {
		var $ = mini.Keyboard;
		switch(_.keyCode) {
		case $.Top:
			this[UD7](this.value + this[NfR]);
			this.PV2();
			break;
		case $.Bottom:
			this[UD7](this.value - this[NfR]);
			this.PV2();
			break
		}
	},
	W8: function(A) {
		if(this[RBE]()) return;
		var $ = A.wheelDelta;
		if(mini.isNull($)) $ = -A.detail * 24;
		var _ = this[NfR];
		if($ < 0) _ = -_;
		this[UD7](this.value + _);
		this.PV2();
		return false
	},
	ZG47: function($) {
		this.MEK();
		M4(document, "mouseup", this.ZG47, this);
		if(this._DownValue != this.getFormValue()) this.PV2()
	},
	SDs: function(A) {
		var _ = this.getValue(),
			$ = parseFloat(this.IJ6.value);
		this[UD7]($);
		if(_ != this.getValue()) this.PV2()
	},
	getAttrs: function($) {
		var _ = HY84[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["minValue", "maxValue", "increment", "decimalPlaces"]);
		return _
	}
});
PC7(HY84, "spinner");
Ukz = function() {
	Ukz[GR_][KNT][GkN](this);
	this[UD7]("00:00:00")
};
Iov(Ukz, OuY, {
	value: null,
	format: "H:mm:ss",
	uiCls: "mini-timespinner",
	DpiHtml: function() {
		var $ = "onmouseover=\"V7(this,'" + this.ME + "');\" " + "onmouseout=\"HMT(this,'" + this.ME + "');\"";
		return "<span class=\"mini-buttonedit-button\" " + $ + "><span class=\"mini-buttonedit-up\"><span></span></span><span class=\"mini-buttonedit-down\"><span></span></span></span>"
	},
	_initEvents: function() {
		Ukz[GR_][S1B][GkN](this);
		IZY2(function() {
			this.on("buttonmousedown", this.BvF, this);
			$v9(this.el, "mousewheel", this.W8, this);
			$v9(this.IJ6, "keydown", this.HtB, this)
		}, this)
	},
	setFormat: function($) {
		if(typeof $ != "string") return;
		var _ = ["H:mm:ss", "HH:mm:ss", "H:mm", "HH:mm", "H", "HH", "mm:ss"];
		if(_.indexOf($) == -1) return;
		if(this.format != $) {
			this.format = $;
			this.IJ6.value = this.getFormattedValue()
		}
	},
	getFormat: function() {
		return this.format
	},
	setValue: function($) {
		$ = mini.parseTime($, this.format);
		if(!$) $ = mini.parseTime("00:00:00", this.format);
		if(mini.isDate($)) $ = new Date($.getTime());
		if(mini.formatDate(this.value, "H:mm:ss") != mini.formatDate($, "H:mm:ss")) {
			this.value = $;
			this.IJ6.value = this.getFormattedValue();
			this.HRA.value = this.getFormValue()
		}
	},
	getValue: function() {
		return this.value == null ? null : new Date(this.value.getTime())
	},
	getFormValue: function() {
		if(!this.value) return "";
		return mini.formatDate(this.value, "H:mm:ss")
	},
	getFormattedValue: function() {
		if(!this.value) return "";
		return mini.formatDate(this.value, this.format)
	},
	AVZt: function(D, C) {
		var $ = this.getValue();
		if($) switch(C) {
		case "hours":
			var A = $.getHours() + D;
			if(A > 23) A = 23;
			if(A < 0) A = 0;
			$.setHours(A);
			break;
		case "minutes":
			var B = $.getMinutes() + D;
			if(B > 59) B = 59;
			if(B < 0) B = 0;
			$.setMinutes(B);
			break;
		case "seconds":
			var _ = $.getSeconds() + D;
			if(_ > 59) _ = 59;
			if(_ < 0) _ = 0;
			$.setSeconds(_);
			break
		} else $ = "00:00:00";
		this[UD7]($)
	},
	TtX: null,
	Xv: function(D, B, C) {
		this.MEK();
		this.AVZt(D, this.$oPV);
		var A = this,
			_ = C,
			$ = new Date();
		this.TtX = setInterval(function() {
			A.AVZt(D, A.$oPV);
			C--;
			if(C == 0 && B > 50) A.Xv(D, B - 100, _ + 3);
			var E = new Date();
			if(E - $ > 500) A.MEK();
			$ = E
		}, B);
		$v9(document, "mouseup", this.ZG47, this)
	},
	MEK: function() {
		clearInterval(this.TtX);
		this.TtX = null
	},
	BvF: function($) {
		this._DownValue = this.getFormValue();
		this.$oPV = "hours";
		if($.spinType == "up") this.Xv(1, 230, 2);
		else this.Xv(-1, 230, 2)
	},
	ZG47: function($) {
		this.MEK();
		M4(document, "mouseup", this.ZG47, this);
		if(this._DownValue != this.getFormValue()) this.PV2()
	},
	SDs: function(_) {
		var $ = this.getFormValue();
		this[UD7](this.IJ6.value);
		if($ != this.getFormValue()) this.PV2()
	},
	getAttrs: function($) {
		var _ = Ukz[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["format"]);
		return _
	}
});
PC7(Ukz, "timespinner");
KHy = function() {
	KHy[GR_][KNT][GkN](this);
	this.on("validation", this.H8, this)
};
Iov(KHy, OuY, {
	width: 180,
	buttonText: "\u6d4f\u89c8...",
	_buttonWidth: 56,
	limitType: "",
	limitTypeErrorText: "\u4e0a\u4f20\u6587\u4ef6\u683c\u5f0f\u4e3a\uff1a",
	allowInput: false,
	readOnly: true,
	QWQ: 0,
	uiCls: "mini-htmlfile",
	_create: function() {
		KHy[GR_][Sir][GkN](this);
		this.Bd8 = mini.append(this.el, "<input type=\"file\" hideFocus class=\"mini-htmlfile-file\" name=\"" + this.name + "\" ContentEditable=false/>");
		$v9(this.el, "mousemove", this.VeS, this);
		$v9(this.Bd8, "change", this.OCsH, this)
	},
	DpiHtml: function() {
		var $ = "onmouseover=\"V7(this,'" + this.ME + "');\" " + "onmouseout=\"HMT(this,'" + this.ME + "');\"";
		return "<span class=\"mini-buttonedit-button\" " + $ + ">" + this.buttonText + "</span>"
	},
	OCsH: function($) {
		this.value = this.IJ6.value = this.Bd8.value;
		this.PV2()
	},
	VeS: function(B) {
		var A = B.pageX,
			_ = B.pageY,
			$ = Y3L(this.el);
		A = (A - $.x - 5);
		_ = (_ - $.y - 5);
		if(this.enabled == false) {
			A = -20;
			_ = -20
		}
		this.Bd8.style.left = A + "px";
		this.Bd8.style.top = _ + "px"
	},
	H8: function(B) {
		var A = B.value.split("."),
			$ = "*." + A[A.length - 1],
			_ = this.limitType.split(";");
		if(_.length > 0 && _.indexOf($) == -1) {
			B.errorText = this.limitTypeErrorText + this.limitType;
			B.isValid = false
		}
	},
	setName: function($) {
		this.name = $;
		mini.setAttr(this.Bd8, "name", this.name)
	},
	getValue: function() {
		return this.IJ6.value
	},
	setButtonText: function($) {
		this.buttonText = $
	},
	getButtonText: function() {
		return this.buttonText
	},
	setLimitType: function($) {
		this.limitType = $
	},
	getLimitType: function() {
		return this.limitType
	},
	getAttrs: function($) {
		var _ = KHy[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["limitType", "buttonText", "limitTypeErrorText"]);
		return _
	}
});
PC7(KHy, "htmlfile");
Yiw9 = function($) {
	Yiw9[GR_][KNT][GkN](this, $);
	this.on("validation", this.H8, this)
};
Iov(Yiw9, OuY, {
	width: 180,
	buttonText: "\u6d4f\u89c8...",
	_buttonWidth: 56,
	limitTypeErrorText: "\u4e0a\u4f20\u6587\u4ef6\u683c\u5f0f\u4e3a\uff1a",
	readOnly: true,
	QWQ: 0,
	limitSize: "",
	limitType: "",
	typesDescription: "\u4e0a\u4f20\u6587\u4ef6\u683c\u5f0f",
	uploadLimit: 0,
	queueLimit: "",
	flashUrl: "",
	uploadUrl: "",
	uploadOnSelect: false,
	uiCls: "mini-fileupload",
	_create: function() {
		Yiw9[GR_][Sir][GkN](this);
		V7(this.el, "mini-htmlfile");
		this.Bd8 = mini.append(this.el, "<span></span>");
		this.uploadEl = this.Bd8;
		$v9(this.el, "mousemove", this.VeS, this)
	},
	DpiHtml: function() {
		var $ = "onmouseover=\"V7(this,'" + this.ME + "');\" " + "onmouseout=\"HMT(this,'" + this.ME + "');\"";
		return "<span class=\"mini-buttonedit-button\" " + $ + ">" + this.buttonText + "</span>"
	},
	destroy: function($) {
		if(this.VHBX) {
			mini[J9](this.VHBX);
			this.VHBX = null
		}
		Yiw9[GR_][EqU5][GkN](this, $)
	},
	VeS: function(A) {
		var $ = this;
		if(this.enabled == false) return;
		if(!this.swfUpload) {
			var B = new SWFUpload({
				file_post_name: this.name,
				upload_url: $.uploadUrl,
				flash_url: $.flashUrl,
				file_size_limit: $.limitSize,
				file_types: $.limitType,
				file_types_description: $.typesDescription,
				file_upload_limit: parseInt($.uploadLimit),
				file_queue_limit: $.queueLimit,
				file_queued_handler: mini.createDelegate(this.__on_file_queued, this),
				upload_error_handler: mini.createDelegate(this.__on_upload_error, this),
				upload_success_handler: mini.createDelegate(this.__on_upload_success, this),
				upload_complete_handler: mini.createDelegate(this.__on_upload_complete, this),
				button_placeholder: $.uploadEl,
				button_width: 1000,
				button_height: 20,
				button_window_mode: "transparent",
				debug: false
			});
			B.flashReady();
			this.swfUpload = B;
			var _ = this.swfUpload.movieElement;
			_.style.zIndex = 1000;
			_.style.position = "absolute";
			_.style.left = "0px";
			_.style.top = "0px";
			_.style.width = "100%";
			_.style.height = "20px"
		}
	},
	setLimitSize: function($) {
		this.limitSize = $
	},
	setLimitType: function($) {
		this.limitType = $
	},
	setTypesDescription: function($) {
		this.typesDescription = $
	},
	setUploadLimit: function($) {
		this.uploadLimit = $
	},
	setQueueLimit: function($) {
		this.queueLimit = $
	},
	setFlashUrl: function($) {
		this.flashUrl = $
	},
	setUploadUrl: function($) {
		this.uploadUrl = $
	},
	setName: function($) {
		this.name = $
	},
	startUpload: function($) {
		if(this.swfUpload) this.swfUpload.startUpload()
	},
	__on_file_queued: function($) {
		if(this.uploadOnSelect) this.swfUpload.startUpload();
		this[Chg]($.name)
	},
	__on_upload_success: function(_, $) {
		var A = {
			file: _,
			serverData: $
		};
		this.fire("uploadsuccess", A)
	},
	__on_upload_error: function($) {
		var _ = {
			file: $
		};
		this.fire("uploaderror", _)
	},
	__on_upload_complete: function($) {
		this.fire("uploadcomplete", $)
	},
	__fileError: function() {},
	getAttrs: function($) {
		var _ = Yiw9[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["limitType", "limitSize", "flashUrl", "uploadUrl", "uploadLimit", "onuploadsuccess", "onuploaderror", "onuploadcomplete"]);
		mini[XD9s]($, _, ["uploadOnSelect"]);
		return _
	}
});
PC7(Yiw9, "fileupload");
$nLw = function() {
	this.data = [];
	$nLw[GR_][KNT][GkN](this);
	$v9(this.IJ6, "mouseup", this.Hfq, this)
};
Iov($nLw, AU6v, {
	allowInput: true,
	valueField: "id",
	textField: "text",
	delimiter: ",",
	multiSelect: false,
	data: [],
	grid: null,
	uiCls: "mini-lookup",
	destroy: function($) {
		if(this.grid) {
			this.grid.un("selectionchanged", this.UgO, this);
			this.grid.un("load", this.B4S, this);
			this.grid = null
		}
		$nLw[GR_][EqU5][GkN](this, $)
	},
	setMultiSelect: function($) {
		this[V0] = $;
		if(this.grid) this.grid.setMultiSelect($)
	},
	setGrid: function($) {
		if(typeof $ == "string") {
			mini.parse($);
			$ = mini.get($)
		}
		this.grid = mini.getAndCreate($);
		if(this.grid) {
			this.grid.setMultiSelect(this[V0]);
			this.grid.setCheckSelectOnLoad(false);
			this.grid.on("selectionchanged", this.UgO, this);
			this.grid.on("load", this.B4S, this)
		}
	},
	getGrid: function() {
		return this.grid
	},
	setValueField: function($) {
		this[B$Gk] = $
	},
	getValueField: function() {
		return this[B$Gk]
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	getItemValue: function($) {
		return String($[this.valueField])
	},
	getItemText: function($) {
		var _ = $[this.textField];
		return mini.isNull(_) ? "" : String(_)
	},
	B5fK: function(A) {
		if(mini.isNull(A)) A = [];
		var B = [],
			C = [];
		for(var _ = 0, D = A.length; _ < D; _++) {
			var $ = A[_];
			if($) {
				B.push(this[Ez9]($));
				C.push(this[Jach]($))
			}
		}
		return [B.join(this.delimiter), C.join(this.delimiter)]
	},
	GC: function(A) {
		var D = {};
		for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$],
				C = _[this.valueField];
			D[C] = _
		}
		return D
	},
	UgO: function(G) {
		var B = this.GC(this.grid.getData()),
			C = this.GC(this.grid.getSelecteds()),
			F = this.GC(this.data);
		if(this[V0] == false) {
			F = {};
			this.data = []
		}
		var A = {};
		for(var E in F) {
			var $ = F[E];
			if(B[E]) if(C[E]);
			else A[E] = $
		}
		for(var _ = this.data.length - 1; _ >= 0; _--) {
			$ = this.data[_], E = $[this.valueField];
			if(A[E]) this.data.removeAt(_)
		}
		for(E in C) {
			$ = C[E];
			if(!F[E]) this.data.push($)
		}
		var D = this.B5fK(this.data);
		this[UD7](D[0]);
		this[Chg](D[1]);
		this.PV2()
	},
	B4S: function(H) {
		var C = this.value.split(this.delimiter),
			F = {};
		for(var $ = 0, D = C.length; $ < D; $++) {
			var G = C[$];
			F[G] = 1
		}
		var A = this.grid.getData(),
			B = [];
		for($ = 0, D = A.length; $ < D; $++) {
			var _ = A[$],
				E = _[this.valueField];
			if(F[E]) B.push(_)
		}
		this.grid[SsB](B)
	},
	doUpdate: function() {
		$nLw[GR_][Mdk][GkN](this);
		this.IJ6[Egr] = true;
		this.el.style.cursor = "default"
	},
	KPW: function($) {
		$nLw[GR_].KPW[GkN](this, $);
		switch($.keyCode) {
		case 46:
		case 8:
			break;
		case 37:
			break;
		case 39:
			break
		}
	},
	Hfq: function(C) {
		if(this[RBE]()) return;
		var _ = mini.getSelectRange(this.IJ6),
			A = _[0],
			B = _[1],
			$ = this.QPYY(A)
	},
	QPYY: function(E) {
		var _ = -1;
		if(this.text == "") return _;
		var C = this.text.split(this.delimiter),
			$ = 0;
		for(var A = 0, D = C.length; A < D; A++) {
			var B = C[A];
			if($ < E && E <= $ + B.length) {
				_ = A;
				break
			}
			$ = $ + B.length + 1
		}
		return _
	},
	getAttrs: function($) {
		var _ = $nLw[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["grid", "valueField", "textField"]);
		mini[XD9s]($, _, ["multiSelect"]);
		return _
	}
});
PC7($nLw, "lookup");
R8ty = function() {
	R8ty[GR_][KNT][GkN](this);
	this.data = [];
	this[Mdk]()
};
Iov(R8ty, CLi, {
	value: "",
	text: "",
	valueField: "id",
	textField: "text",
	url: "",
	delay: 250,
	allowInput: true,
	editIndex: 0,
	Ppho: "mini-textboxlist-focus",
	UoH: "mini-textboxlist-item-hover",
	D6r4: "mini-textboxlist-item-selected",
	PEJ: "mini-textboxlist-close-hover",
	uiCls: "mini-textboxlist",
	_create: function() {
		var A = "<table class=\"mini-textboxlist\" cellpadding=\"0\" cellspacing=\"0\"><tr ><td class=\"mini-textboxlist-border\"><ul></ul><a href=\"#\"></a><input type=\"hidden\"/></td></tr></table>",
			_ = document.createElement("div");
		_.innerHTML = A;
		this.el = _.firstChild;
		var $ = this.el.getElementsByTagName("td")[0];
		this.ulEl = $.firstChild;
		this.HRA = $.lastChild;
		this.focusEl = $.childNodes[1]
	},
	destroy: function($) {
		if(this.isShowPopup) this[OsRe]();
		M4(document, "mousedown", this.UgY, this);
		R8ty[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		R8ty[GR_][S1B][GkN](this);
		$v9(this.el, "mousemove", this.VeS, this);
		$v9(this.el, "mouseout", this.$MHa, this);
		$v9(this.el, "mousedown", this.AOlf, this);
		$v9(this.el, "click", this.PGY, this);
		$v9(this.el, "keydown", this.HtB, this);
		$v9(document, "mousedown", this.UgY, this)
	},
	UgY: function($) {
		if(this[RBE]() || this.allowInput == false) return false;
		if(this.isShowPopup) if(!TWAc(this.popup.el, $.target)) this[OsRe]();
		if(this._j) if(this[LU]($) == false) {
			this[M$](null, false);
			this.showInput(false);
			this[NeI](this.Ppho);
			this._j = false
		}
	},
	errorIconEl: null,
	getErrorIconEl: function() {
		if(!this.Izgy) {
			var _ = this.el.rows[0],
				$ = _.insertCell(1);
			$.style.cssText = "width:18px;vertical-align:top;";
			$.innerHTML = "<div class=\"mini-errorIcon\"></div>";
			this.Izgy = $.firstChild
		}
		return this.Izgy
	},
	Kg: function() {
		if(this.Izgy) jQuery(this.Izgy.parentNode).remove();
		this.Izgy = null
	},
	doLayout: function() {
		if(this.canLayout() == false) return;
		R8ty[GR_][Fcv][GkN](this);
		if(this[RBE]() || this.allowInput == false) this.ME_[Egr] = true;
		else this.ME_[Egr] = false
	},
	doUpdate: function() {
		if(this.Z5A) clearInterval(this.Z5A);
		if(this.ME_) M4(this.ME_, "keydown", this.KPW, this);
		var G = [],
			F = this.uid;
		for(var A = 0, E = this.data.length; A < E; A++) {
			var _ = this.data[A],
				C = F + "$text$" + A,
				B = _[this.textField];
			if(mini.isNull(B)) B = "";
			G[G.length] = "<li id=\"" + C + "\" class=\"mini-textboxlist-item\">";
			G[G.length] = B;
			G[G.length] = "<span class=\"mini-textboxlist-close\"></span></li>"
		}
		var $ = F + "$input";
		G[G.length] = "<li id=\"" + $ + "\" class=\"mini-textboxlist-inputLi\"><input class=\"mini-textboxlist-input\" type=\"text\" autocomplete=\"off\"></li>";
		this.ulEl.innerHTML = G.join("");
		this.editIndex = this.data.length;
		if(this.editIndex < 0) this.editIndex = 0;
		this.inputLi = this.ulEl.lastChild;
		this.ME_ = this.inputLi.firstChild;
		$v9(this.ME_, "keydown", this.KPW, this);
		var D = this;
		this.ME_.onkeyup = function() {
			D.Bikt()
		};
		D.Z5A = null;
		D.P1ZF = D.ME_.value;
		this.ME_.onfocus = function() {
			D.Z5A = setInterval(function() {
				if(D.P1ZF != D.ME_.value) {
					D.F62b();
					D.P1ZF = D.ME_.value
				}
			}, 10);
			D[QlR](D.Ppho);
			D._j = true
		};
		this.ME_.onblur = function() {
			clearInterval(D.Z5A)
		}
	},
	Tlpp: function(_) {
		var A = ZW(_.target, "mini-textboxlist-item");
		if(A) {
			var $ = A.id.split("$"),
				B = $[$.length - 1];
			return this.data[B]
		}
	},
	getItem: function($) {
		if(typeof $ == "number") return this.data[$];
		if(typeof $ == "object") return $
	},
	getItemEl: function(_) {
		var $ = this.data.indexOf(_),
			A = this.uid + "$text$" + $;
		return document.getElementById(A)
	},
	hoverItem: function($, A) {
		this.blurItem();
		var _ = this.getItemEl($);
		V7(_, this.UoH);
		if(A && XPZ(A.target, "mini-textboxlist-close")) V7(A.target, this.PEJ)
	},
	blurItem: function() {
		var _ = this.data.length;
		for(var A = 0, C = _; A < C; A++) {
			var $ = this.data[A],
				B = this.getItemEl($);
			if(B) {
				HMT(B, this.UoH);
				HMT(B.lastChild, this.PEJ)
			}
		}
	},
	showInput: function(A) {
		this[M$](null);
		if(mini.isNumber(A)) this.editIndex = A;
		else this.editIndex = this.data.length;
		if(this.editIndex < 0) this.editIndex = 0;
		if(this.editIndex > this.data.length) this.editIndex = this.data.length;
		var B = this.inputLi;
		B.style.display = "block";
		if(mini.isNumber(A) && A < this.data.length) {
			var _ = this.data[A],
				$ = this.getItemEl(_);
			jQuery($).before(B)
		} else this.ulEl.appendChild(B);
		if(A !== false) setTimeout(function() {
			try {
				B.firstChild.focus();
				mini.selectRange(B.firstChild, 100)
			} catch($) {}
		}, 10);
		else {
			this.lastInputText = "";
			this.ME_.value = ""
		}
		return B
	},
	select: function(_) {
		_ = this[GmG](_);
		if(this.YF) {
			var $ = this.getItemEl(this.YF);
			HMT($, this.D6r4)
		}
		this.YF = _;
		if(this.YF) {
			$ = this.getItemEl(this.YF);
			V7($, this.D6r4)
		}
		var A = this;
		if(this.YF) this.focusEl.focus();
		if(this.YF) {
			A[QlR](A.Ppho);
			A._j = true
		}
	},
	Acs: function() {
		var _ = this.YkMq[JFP](),
			$ = this.editIndex;
		if(_) {
			_ = mini.clone(_);
			this.insertItem($, _)
		}
	},
	insertItem: function(_, $) {
		this.data.insert(_, $);
		var B = this.getText(),
			A = this.getValue();
		this[UD7](A, false);
		this[Chg](B, false);
		this.Jdo();
		this[Mdk]();
		this.showInput(_ + 1);
		this.PV2()
	},
	removeItem: function(_) {
		if(!_) return;
		var $ = this.getItemEl(_);
		mini[BnX]($);
		this.data.remove(_);
		var B = this.getText(),
			A = this.getValue();
		this[UD7](A, false);
		this[Chg](B, false);
		this.PV2()
	},
	Jdo: function() {
		var C = (this.text ? this.text : "").split(","),
			B = (this.value ? this.value : "").split(",");
		if(B[0] == "") B = [];
		var _ = B.length;
		this.data.length = _;
		for(var A = 0, D = _; A < D; A++) {
			var $ = this.data[A];
			if(!$) {
				$ = {};
				this.data[A] = $
			}
			$[this.textField] = !mini.isNull(C[A]) ? C[A] : "";
			$[this.valueField] = !mini.isNull(B[A]) ? B[A] : ""
		}
		this.value = this.getValue();
		this.text = this.getText()
	},
	getInputText: function() {
		return this.ME_ ? this.ME_.value : ""
	},
	getText: function() {
		var C = [];
		for(var _ = 0, A = this.data.length; _ < A; _++) {
			var $ = this.data[_],
				B = $[this.textField];
			if(mini.isNull(B)) B = "";
			B = B.replace(",", "\uff0c");
			C.push(B)
		}
		return C.join(",")
	},
	getValue: function() {
		var B = [];
		for(var _ = 0, A = this.data.length; _ < A; _++) {
			var $ = this.data[_];
			B.push($[this.valueField])
		}
		return B.join(",")
	},
	setName: function($) {
		if(this.name != $) {
			this.name = $;
			this.HRA.name = $
		}
	},
	setValue: function($) {
		if(mini.isNull($)) $ = "";
		if(this.value != $) {
			this.value = $;
			this.HRA.value = $;
			this.Jdo();
			this[Mdk]()
		}
	},
	setText: function($) {
		if(mini.isNull($)) $ = "";
		if(this.text !== $) {
			this.text = $;
			this.Jdo();
			this[Mdk]()
		}
	},
	setValueField: function($) {
		this[B$Gk] = $
	},
	getValueField: function() {
		return this[B$Gk]
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setAllowInput: function($) {
		this.allowInput = $;
		this[Fcv]()
	},
	getAllowInput: function() {
		return this.allowInput
	},
	setUrl: function($) {
		this.url = $
	},
	getUrl: function() {
		return this.url
	},
	setPopupHeight: function($) {
		this[Dzv] = $
	},
	getPopupHeight: function() {
		return this[Dzv]
	},
	setPopupMinHeight: function($) {
		this[Jrd] = $
	},
	getPopupMinHeight: function() {
		return this[Jrd]
	},
	setPopupMaxHeight: function($) {
		this[OsZw] = $
	},
	getPopupMaxHeight: function() {
		return this[OsZw]
	},
	Bikt: function() {
		if(this[WH4]() == false) return;
		var _ = this.getInputText(),
			B = mini.measureText(this.ME_, _),
			$ = B.width > 20 ? B.width + 4 : 20,
			A = R0(this.el, true);
		if($ > A - 15) $ = A - 15;
		this.ME_.style.width = $ + "px"
	},
	F62b: function(_) {
		var $ = this;
		setTimeout(function() {
			$.Bikt()
		}, 1);
		this[Zpt]("loading");
		this.MyA();
		this.delayTimer = setTimeout(function() {
			var _ = $.ME_.value;
			$.MyRg()
		}, this.delay)
	},
	MyRg: function() {
		if(this[WH4]() == false) return;
		var _ = this.getInputText(),
			A = this,
			$ = this.YkMq.getData(),
			B = {
				key: _,
				value: this.getValue(),
				text: this.getText()
			},
			C = this.url,
			E = typeof C == "function" ? C : window[C];
		if(typeof E == "function") C = E(this);
		var D = {
			url: C,
			async: true,
			data: B,
			type: "GET",
			cache: false,
			dataType: "text",
			cancel: false
		};
		this.fire("beforeload", D);
		if(D.cancel) return;
		mini.copyTo(D, {
			success: function($) {
				var _ = mini.decode($);
				A.YkMq[OUQ](_);
				A[Zpt]();
				A.YkMq.Kro(0, true);
				A.fire("load")
			},
			error: function($, B, _) {
				A[Zpt]("error")
			}
		});
		A.HIU = jQuery.ajax(D)
	},
	MyA: function() {
		if(this.delayTimer) {
			clearTimeout(this.delayTimer);
			this.delayTimer = null
		}
		if(this.HIU) this.HIU.abort()
	},
	within: function($) {
		if(TWAc(this.el, $.target)) return true;
		if(this[Zpt] && this.popup && this.popup[LU]($)) return true;
		return false
	},
	popupLoadingText: "<span class='mini-textboxlist-popup-loading'>Loading...</span>",
	popupErrorText: "<span class='mini-textboxlist-popup-error'>Error</span>",
	popupEmptyText: "<span class='mini-textboxlist-popup-noresult'>No Result</span>",
	isShowPopup: false,
	popupHeight: "",
	popupMinHeight: 30,
	popupMaxHeight: 150,
	GP8v: function() {
		if(!this.popup) {
			this.popup = new YTBL();
			this.popup[QlR]("mini-textboxlist-popup");
			this.popup.setStyle("position:absolute;left:0;top:0;");
			this.popup[PN] = true;
			this.popup[$J](this[B$Gk]);
			this.popup.setTextField(this[LOda]);
			this.popup[Ivp](document.body);
			this.popup.on("itemclick", function($) {
				this[OsRe]();
				this.Acs()
			}, this)
		}
		this.YkMq = this.popup;
		return this.popup
	},
	showPopup: function($) {
		this.isShowPopup = true;
		var _ = this.GP8v();
		_.el.style.zIndex = mini.getMaxZIndex();
		var B = this.YkMq;
		B[QGN] = this.popupEmptyText;
		if($ == "loading") {
			B[QGN] = this.popupLoadingText;
			this.YkMq[OUQ]([])
		} else if($ == "error") {
			B[QGN] = this.popupLoadingText;
			this.YkMq[OUQ]([])
		}
		this.YkMq[Mdk]();
		var A = this.getBox(),
			D = A.x,
			C = A.y + A.height;
		this.popup.el.style.display = "block";
		mini[Wib](_.el, -1000, -1000);
		this.popup[_wJ](A.width);
		this.popup[P4](this[Dzv]);
		if(this.popup[DTTy]() < this[Jrd]) this.popup[P4](this[Jrd]);
		if(this.popup[DTTy]() > this[OsZw]) this.popup[P4](this[OsZw]);
		mini[Wib](_.el, D, C)
	},
	hidePopup: function() {
		this.isShowPopup = false;
		if(this.popup) this.popup.el.style.display = "none"
	},
	VeS: function(_) {
		if(this.enabled == false) return;
		var $ = this.Tlpp(_);
		if(!$) {
			this.blurItem();
			return
		}
		this.hoverItem($, _)
	},
	$MHa: function($) {
		this.blurItem()
	},
	PGY: function(_) {
		if(this.enabled == false) return;
		if(this[RBE]() || this.allowInput == false) return;
		var $ = this.Tlpp(_);
		if(!$) {
			if(ZW(_.target, "mini-textboxlist-input"));
			else this.showInput();
			return
		}
		this.focusEl.focus();
		this[M$]($);
		if(_ && XPZ(_.target, "mini-textboxlist-close")) this.removeItem($)
	},
	HtB: function(B) {
		if(this[RBE]() || this.allowInput == false) return false;
		var $ = this.data.indexOf(this.YF),
			_ = this;

		function A() {
			var A = _.data[$];
			_.removeItem(A);
			A = _.data[$];
			if(!A) A = _.data[$ - 1];
			_[M$](A);
			if(!A) _.showInput()
		}
		switch(B.keyCode) {
		case 8:
			B.preventDefault();
			A();
			break;
		case 37:
		case 38:
			this[M$](null);
			this.showInput($);
			break;
		case 39:
		case 40:
			$ += 1;
			this[M$](null);
			this.showInput($);
			break;
		case 46:
			A();
			break
		}
	},
	KPW: function(G) {
		if(this[RBE]() || this.allowInput == false) return false;
		G.stopPropagation();
		if(this[RBE]() || this.allowInput == false) return;
		var E = mini.getSelectRange(this.ME_),
			B = E[0],
			D = E[1],
			F = this.ME_.value.length,
			C = B == D && B == 0,
			A = B == D && D == F;
		if(this[RBE]() || this.allowInput == false) G.preventDefault();
		if(G.keyCode == 9) {
			this[OsRe]();
			return
		}
		if(G.keyCode == 16 || G.keyCode == 17 || G.keyCode == 18) return;
		switch(G.keyCode) {
		case 13:
			if(this.isShowPopup) {
				G.preventDefault();
				var _ = this.YkMq.getFocusedItem();
				if(_) this.YkMq[UXi](_);
				this.lastInputText = this.text;
				this[OsRe]();
				this.Acs()
			}
			break;
		case 27:
			G.preventDefault();
			this[OsRe]();
			break;
		case 8:
			if(C) G.preventDefault();
		case 37:
			if(C) if(this.isShowPopup) this[OsRe]();
			else if(this.editIndex > 0) {
				var $ = this.editIndex - 1;
				if($ < 0) $ = 0;
				if($ >= this.data.length) $ = this.data.length - 1;
				this.showInput(false);
				this[M$]($)
			}
			break;
		case 39:
			if(A) if(this.isShowPopup) this[OsRe]();
			else if(this.editIndex <= this.data.length - 1) {
				$ = this.editIndex;
				this.showInput(false);
				this[M$]($)
			}
			break;
		case 38:
			G.preventDefault();
			if(this.isShowPopup) {
				$ = -1, _ = this.YkMq.getFocusedItem();
				if(_) $ = this.YkMq.indexOf(_);
				$--;
				if($ < 0) $ = 0;
				this.YkMq.Kro($, true)
			}
			break;
		case 40:
			G.preventDefault();
			if(this.isShowPopup) {
				$ = -1, _ = this.YkMq.getFocusedItem();
				if(_) $ = this.YkMq.indexOf(_);
				$++;
				if($ < 0) $ = 0;
				if($ >= this.YkMq.getCount()) $ = this.YkMq.getCount() - 1;
				this.YkMq.Kro($, true)
			} else this.F62b(true);
			break;
		default:
			break
		}
	},
	focus: function() {
		try {
			this.ME_.focus()
		} catch($) {}
	},
	blur: function() {
		try {
			this.ME_.blur()
		} catch($) {}
	},
	getAttrs: function($) {
		var A = Pg3R[GR_][$gN][GkN](this, $),
			_ = jQuery($);
		mini[ENl]($, A, ["value", "text", "valueField", "textField", "url", "popupHeight"]);
		mini[XD9s]($, A, ["allowInput"]);
		mini[GGt]($, A, ["popupMinHeight", "popupMaxHeight"]);
		return A
	}
});
PC7(R8ty, "textboxlist");
FGFZ = function() {
	FGFZ[GR_][KNT][GkN](this);
	var $ = this;
	$.Z5A = null;
	this.IJ6.onfocus = function() {
		$.P1ZF = $.IJ6.value;
		$.Z5A = setInterval(function() {
			if($.P1ZF != $.IJ6.value) {
				$._e8();
				$.P1ZF = $.IJ6.value;
				if($.IJ6.value == "" && $.value != "") {
					$[UD7]("");
					$.PV2()
				}
			}
		}, 10)
	};
	this.IJ6.onblur = function() {
		clearInterval($.Z5A);
		if(!$.isShowPopup()) if($.P1ZF != $.IJ6.value) if($.IJ6.value == "" && $.value != "") {
			$[UD7]("");
			$.PV2()
		}
	};
	this._buttonEl.style.display = "none"
};
Iov(FGFZ, ZjG, {
	url: "",
	allowInput: true,
	delay: 250,
	_buttonWidth: 0,
	uiCls: "mini-autocomplete",
	setUrl: function($) {
		this.url = $
	},
	setValue: function($) {
		if(this.value != $) {
			this.value = $;
			this.HRA.value = this.value
		}
	},
	setText: function($) {
		if(this.text != $) {
			this.text = $;
			this.P1ZF = $
		}
		this.IJ6.value = this.text
	},
	popupLoadingText: "<span class='mini-textboxlist-popup-loading'>Loading...</span>",
	popupErrorText: "<span class='mini-textboxlist-popup-error'>Error</span>",
	popupEmptyText: "<span class='mini-textboxlist-popup-noresult'>No Result</span>",
	showPopup: function($) {
		var _ = this.getPopup(),
			A = this.YkMq;
		A[PN] = true;
		A[QGN] = this.popupEmptyText;
		if($ == "loading") {
			A[QGN] = this.popupLoadingText;
			this.YkMq[OUQ]([])
		} else if($ == "error") {
			A[QGN] = this.popupLoadingText;
			this.YkMq[OUQ]([])
		}
		this.YkMq[Mdk]();
		FGFZ[GR_][Zpt][GkN](this)
	},
	KPW: function(C) {
		this.fire("keydown", {
			htmlEvent: C
		});
		if(C.keyCode == 8 && (this[RBE]() || this.allowInput == false)) return false;
		if(C.keyCode == 9) {
			this[OsRe]();
			return
		}
		switch(C.keyCode) {
		case 27:
			if(this.isShowPopup()) C.stopPropagation();
			this[OsRe]();
			break;
		case 13:
			if(this.isShowPopup()) {
				C.preventDefault();
				C.stopPropagation();
				var _ = this.YkMq.getFocusedIndex();
				if(_ != -1) {
					var $ = this.YkMq.getAt(_),
						B = this.YkMq.B5fK([$]),
						A = B[0];
					this[UD7](A);
					this[Chg](B[1]);
					this.PV2();
					this[OsRe]()
				}
			} else this.fire("enter");
			break;
		case 37:
			break;
		case 38:
			_ = this.YkMq.getFocusedIndex();
			if(_ == -1) {
				_ = 0;
				if(!this[V0]) {
					$ = this.YkMq.findItems(this.value)[0];
					if($) _ = this.YkMq.indexOf($)
				}
			}
			if(this.isShowPopup()) if(!this[V0]) {
				_ -= 1;
				if(_ < 0) _ = 0;
				this.YkMq.Kro(_, true)
			}
			break;
		case 39:
			break;
		case 40:
			_ = this.YkMq.getFocusedIndex();
			if(this.isShowPopup()) {
				if(!this[V0]) {
					_ += 1;
					if(_ > this.YkMq.getCount() - 1) _ = this.YkMq.getCount() - 1;
					this.YkMq.Kro(_, true)
				}
			} else this._e8(this.IJ6.value);
			break;
		default:
			this._e8(this.IJ6.value);
			break
		}
	},
	_e8: function(_) {
		var $ = this;
		if(this._queryTimer) {
			clearTimeout(this._queryTimer);
			this._queryTimer = null
		}
		this._queryTimer = setTimeout(function() {
			var _ = $.IJ6.value;
			$.MyRg(_)
		}, this.delay);
		this[Zpt]("loading")
	},
	MyRg: function($) {
		if(this.HIU) this.HIU.abort();
		var _ = this;
		this.HIU = jQuery.ajax({
			url: this.url,
			data: {
				key: $
			},
			async: true,
			cache: false,
			dataType: "text",
			success: function($) {
				var A = mini.decode($);
				_.YkMq[OUQ](A);
				_[Zpt]();
				_.YkMq.Kro(0, true);
				_.fire("load")
			},
			error: function($, B, A) {
				_[Zpt]("error")
			}
		})
	},
	getAttrs: function($) {
		var A = FGFZ[GR_][$gN][GkN](this, $),
			_ = jQuery($);
		return A
	}
});
PC7(FGFZ, "autocomplete");
mini.Form = function($) {
	this.el = Hcd($);
	if(!this.el) throw new Error("form element not null");
	mini.Form[GR_][KNT][GkN](this)
};
Iov(mini.Form, SgZ, {
	el: null,
	getFields: function() {
		if(!this.el) return [];
		var $ = mini.findControls(function($) {
			if(!$.el || $.formField != true) return false;
			if(TWAc(this.el, $.el)) return true;
			return false
		}, this);
		return $
	},
	getFieldsMap: function() {
		var B = this.getFields(),
			A = {};
		for(var $ = 0, C = B.length; $ < C; $++) {
			var _ = B[$];
			if(_.name) A[_.name] = _
		}
		return A
	},
	getField: function($) {
		if(!this.el) return null;
		return mini.getbyName($, this.el)
	},
	getData: function(B) {
		var A = B ? "getFormValue" : "getValue",
			$ = this.getFields(),
			D = {};
		for(var _ = 0, E = $.length; _ < E; _++) {
			var C = $[_],
				F = C[A];
			if(!F) continue;
			if(C.name) D[C.name] = F[GkN](C);
			if(C.textName && C.getText) D[C.textName] = C.getText()
		}
		return D
	},
	setData: function(E, A) {
		if(typeof E != "object") E = {};
		var B = this.getFieldsMap();
		for(var C in B) {
			var _ = B[C];
			if(!_) continue;
			if(_[UD7]) {
				var D = E[C];
				if(D === undefined && A === false) continue;
				if(D === null) D = "";
				_[UD7](D)
			}
			if(_[Chg] && _.textName) {
				var $ = E[_.textName] || "";
				_[Chg]($)
			}
		}
	},
	reset: function() {
		var $ = this.getFields();
		for(var _ = 0, B = $.length; _ < B; _++) {
			var A = $[_];
			if(!A[UD7]) continue;
			A[UD7](A[$o_])
		}
		this.setIsValid(true)
	},
	clear: function() {
		var $ = this.getFields();
		for(var _ = 0, B = $.length; _ < B; _++) {
			var A = $[_];
			if(!A[UD7]) continue;
			A[UD7]("")
		}
		this.setIsValid(true)
	},
	validate: function(C) {
		var $ = this.getFields();
		for(var _ = 0, D = $.length; _ < D; _++) {
			var A = $[_];
			if(!A[UzD]) continue;
			var B = A[UzD]();
			if(B == false && C === false) break
		}
		return this.isValid()
	},
	setIsValid: function(B) {
		var $ = this.getFields();
		for(var _ = 0, C = $.length; _ < C; _++) {
			var A = $[_];
			if(!A.setIsValid) continue;
			A.setIsValid(B)
		}
	},
	isValid: function() {
		var $ = this.getFields();
		for(var _ = 0, B = $.length; _ < B; _++) {
			var A = $[_];
			if(!A.isValid) continue;
			if(A.isValid() == false) return false
		}
		return true
	},
	getErrorTexts: function() {
		var A = [],
			_ = this.getErrors();
		for(var $ = 0, C = _.length; $ < C; $++) {
			var B = _[$];
			A.push(B.errorText)
		}
		return A
	},
	getErrors: function() {
		var A = [],
			$ = this.getFields();
		for(var _ = 0, C = $.length; _ < C; _++) {
			var B = $[_];
			if(!B.isValid) continue;
			if(B.isValid() == false) A.push(B)
		}
		return A
	},
	mask: function($) {
		if(typeof $ == "string") $ = {
			html: $
		};
		$ = $ || {};
		$.el = this.el;
		if(!$.cls) $.cls = this.$tJo;
		mini.mask($)
	},
	unmask: function() {
		mini.unmask(this.el)
	},
	$tJo: "mini-mask-loading",
	loadingMsg: "\u6570\u636e\u52a0\u8f7d\u4e2d\uff0c\u8bf7\u7a0d\u540e...",
	loading: function($) {
		this.mask($ || this.loadingMsg)
	},
	OzPb: function($) {
		this._changed = true
	},
	_changed: false,
	setChanged: function(A) {
		this._changed = A;
		var $ = form.getFields();
		for(var _ = 0, C = $.length; _ < C; _++) {
			var B = $[_];
			B.on("valuechanged", this.OzPb, this)
		}
	},
	isChanged: function() {
		return this._changed
	},
	setEnabled: function(A) {
		var $ = form.getFields();
		for(var _ = 0, C = $.length; _ < C; _++) {
			var B = $[_];
			B.setEnabled(A)
		}
	}
});
ECe = function() {
	ECe[GR_][KNT][GkN](this)
};
Iov(ECe, FIa, {
	style: "",
	_clearBorder: false,
	uiCls: "mini-fit",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-fit";
		this.H23I = this.el
	},
	_initEvents: function() {},
	isFixedSize: function() {
		return false
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		var _ = this.el.parentNode,
			A = mini[OIAh](_),
			B = KwY(_, true);
		for(var $ = 0, G = A.length; $ < G; $++) {
			var E = A[$];
			if(E == this.el) continue;
			var D = KwY(E),
				H = TA$(E);
			B = B - D - H.top - H.bottom
		}
		var F = D0uu(this.el),
			C = VO(this.el),
			H = TA$(this.el);
		B = B - H.top - H.bottom;
		if(jQuery.boxModel) B = B - C.top - C.bottom - F.top - F.bottom;
		if(B < 0) B = 0;
		this.el.style.height = B + "px";
		try {
			A = mini[OIAh](this.el);
			for($ = 0, G = A.length; $ < G; $++) {
				E = A[$];
				mini.layout(E)
			}
		} catch(I) {}
	},
	set_bodyParent: function($) {
		if(!$) return;
		var _ = this.H23I,
			A = $;
		while(A.firstChild) {
			try {
				_.appendChild(A.firstChild)
			} catch(B) {}
		}
		this[Fcv]()
	},
	getAttrs: function($) {
		var _ = ECe[GR_][$gN][GkN](this, $);
		_._bodyParent = $;
		return _
	}
});
PC7(ECe, "fit");
NpV8 = function() {
	this.JOG();
	NpV8[GR_][KNT][GkN](this);
	if(this.url) this.setUrl(this.url)
};
Iov(NpV8, FIa, {
	width: 250,
	title: "",
	iconCls: "",
	iconStyle: "",
	url: "",
	refreshOnExpand: false,
	maskOnLoad: true,
	showCollapseButton: false,
	showCloseButton: false,
	closeAction: "display",
	showHeader: true,
	showToolbar: false,
	showFooter: false,
	headerCls: "",
	headerStyle: "",
	bodyCls: "",
	bodyStyle: "",
	footerCls: "",
	footerStyle: "",
	toolbarCls: "",
	toolbarStyle: "",
	set: function(A) {
		if(typeof A == "string") return this;
		var $ = this.ERW;
		this.ERW = false;
		var C = A.toolbar;
		delete A.toolbar;
		var _ = A.footer;
		delete A.footer;
		var B = A.url;
		delete A.url;
		NpV8[GR_].set[GkN](this, A);
		if(C) this.setToolbar(C);
		if(_) this.setFooter(_);
		if(B) this.setUrl(B);
		this.ERW = $;
		this[Fcv]();
		return this
	},
	uiCls: "mini-panel",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-panel";
		var _ = "<div class=\"mini-panel-border\">" + "<div class=\"mini-panel-header\" ><div class=\"mini-panel-header-inner\" ><span class=\"mini-panel-icon\"></span><div class=\"mini-panel-title\" ></div><div class=\"mini-tools\" ></div></div></div>" + "<div class=\"mini-panel-viewport\">" + "<div class=\"mini-panel-toolbar\"></div>" + "<div class=\"mini-panel-body\" ></div>" + "<div class=\"mini-panel-footer\"></div>" + "<div class=\"mini-panel-resizeGrid\"></div>" + "</div>" + "</div>";
		this.el.innerHTML = _;
		this.TG = this.el.firstChild;
		this.NL = this.TG.firstChild;
		this.OgC = this.TG.lastChild;
		this.B4N = mini.byClass("mini-panel-toolbar", this.el);
		this.H23I = mini.byClass("mini-panel-body", this.el);
		this.Yj = mini.byClass("mini-panel-footer", this.el);
		this.Vhh = mini.byClass("mini-panel-resizeGrid", this.el);
		var $ = mini.byClass("mini-panel-header-inner", this.el);
		this.RRre = mini.byClass("mini-panel-icon", this.el);
		this.BoB = mini.byClass("mini-panel-title", this.el);
		this.Be = mini.byClass("mini-tools", this.el);
		Gn(this.H23I, this.bodyStyle);
		this[Mdk]()
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this)
		}, this)
	},
	doUpdate: function() {
		this.BoB.innerHTML = this.title;
		this.RRre.style.display = (this.iconCls || this[DP]) ? "inline" : "none";
		this.RRre.className = "mini-panel-icon " + this.iconCls;
		Gn(this.RRre, this[DP]);
		this.NL.style.display = this.showHeader ? "" : "none";
		this.B4N.style.display = this[GYYg] ? "" : "none";
		this.Yj.style.display = this[B8] ? "" : "none";
		var A = "";
		for(var $ = this.buttons.length - 1; $ >= 0; $--) {
			var _ = this.buttons[$];
			A += "<span id=\"" + $ + "\" class=\"" + _.cls + " " + (_.enabled ? "" : "mini-disabled") + "\" style=\"" + _.style + ";" + (_.visible ? "" : "display:none;") + "\"></span>"
		}
		this.Be.innerHTML = A;
		this[Fcv]()
	},
	count: 1,
	doLayout: function() {
		if(!this.canLayout()) return;
		this.Vhh.style.display = this[JkX] ? "" : "none";
		this.H23I.style.height = "";
		this.H23I.style.width = "";
		this.NL.style.width = "";
		this.OgC.style.width = "";
		var F = this[_H](),
			C = this[J4j](),
			_ = VO(this.H23I),
			G = D0uu(this.H23I),
			J = TA$(this.H23I),
			$ = this[Y1Q](true),
			E = $;
		$ = $ - J.left - J.right;
		if(jQuery.boxModel) $ = $ - _.left - _.right - G.left - G.right;
		if($ < 0) $ = 0;
		this.H23I.style.width = $ + "px";
		$ = E;
		this.NL.style.width = $ + "px";
		this.B4N.style.width = $ + "px";
		this.Yj.style.width = "auto";
		if(!F) {
			var I = D0uu(this.TG),
				A = this[DTTy](true),
				B = this.showHeader ? jQuery(this.NL).outerHeight() : 0,
				D = this[GYYg] ? jQuery(this.B4N).outerHeight() : 0,
				H = this[B8] ? jQuery(this.Yj).outerHeight() : 0;
			this.OgC.style.height = (A - B) + "px";
			A = A - B - D - H;
			if(jQuery.boxModel) A = A - _.top - _.bottom - G.top - G.bottom;
			A = A - J.top - J.bottom;
			if(A < 0) A = 0;
			this.H23I.style.height = A + "px"
		}
		mini.layout(this.TG)
	},
	setHeaderStyle: function($) {
		this.headerStyle = $;
		Gn(this.NL, $);
		this[Fcv]()
	},
	getHeaderStyle: function() {
		return this.headerStyle
	},
	setBodyStyle: function($) {
		this.bodyStyle = $;
		Gn(this.H23I, $);
		this[Fcv]()
	},
	getBodyStyle: function() {
		return this.bodyStyle
	},
	setToolbarStyle: function($) {
		this.toolbarStyle = $;
		Gn(this.B4N, $);
		this[Fcv]()
	},
	getToolbarStyle: function() {
		return this.toolbarStyle
	},
	setFooterStyle: function($) {
		this.footerStyle = $;
		Gn(this.Yj, $);
		this[Fcv]()
	},
	getFooterStyle: function() {
		return this.footerStyle
	},
	setHeaderCls: function($) {
		jQuery(this.NL)[ZcNC](this.headerCls);
		jQuery(this.NL)[Mc]($);
		this.headerCls = $;
		this[Fcv]()
	},
	getHeaderCls: function() {
		return this.headerCls
	},
	setBodyCls: function($) {
		jQuery(this.H23I)[ZcNC](this.bodyCls);
		jQuery(this.H23I)[Mc]($);
		this.bodyCls = $;
		this[Fcv]()
	},
	getBodyCls: function() {
		return this.bodyCls
	},
	setToolbarCls: function($) {
		jQuery(this.B4N)[ZcNC](this.toolbarCls);
		jQuery(this.B4N)[Mc]($);
		this.toolbarCls = $;
		this[Fcv]()
	},
	getToolbarCls: function() {
		return this.toolbarCls
	},
	setFooterCls: function($) {
		jQuery(this.Yj)[ZcNC](this.footerCls);
		jQuery(this.Yj)[Mc]($);
		this.footerCls = $;
		this[Fcv]()
	},
	getFooterCls: function() {
		return this.footerCls
	},
	setTitle: function($) {
		this.title = $;
		this[Mdk]()
	},
	getTitle: function() {
		return this.title
	},
	setIconCls: function($) {
		this.iconCls = $;
		this[Mdk]()
	},
	getIconCls: function() {
		return this.iconCls
	},
	setShowCloseButton: function($) {
		this[$KS] = $;
		var _ = this.getButton("close");
		_.visible = $;
		if(_) this[Mdk]()
	},
	getShowCloseButton: function() {
		return this[$KS]
	},
	setCloseAction: function($) {
		this[Xfv] = $
	},
	getCloseAction: function() {
		return this[Xfv]
	},
	setShowCollapseButton: function($) {
		this[CJP] = $;
		var _ = this.getButton("collapse");
		_.visible = $;
		if(_) this[Mdk]()
	},
	getShowCollapseButton: function() {
		return this[CJP]
	},
	setShowHeader: function($) {
		this.showHeader = $;
		this[Mdk]()
	},
	getShowHeader: function() {
		return this.showHeader
	},
	setShowToolbar: function($) {
		this[GYYg] = $;
		this[Mdk]()
	},
	getShowToolbar: function() {
		return this[GYYg]
	},
	setShowFooter: function($) {
		this[B8] = $;
		this[Mdk]()
	},
	getShowFooter: function() {
		return this[B8]
	},
	PGY: function(A) {
		var $ = ZW(A.target, "mini-tools");
		if($) {
			var _ = this.getButton(parseInt(A.target.id));
			if(_) this.Ii(_, A)
		}
	},
	Ii: function(B, $) {
		var C = {
			button: B,
			index: this.buttons.indexOf(B),
			name: B.name.toLowerCase(),
			htmlEvent: $,
			cancel: false
		};
		this.fire("beforebuttonclick", C);
		try {
			if(C.name == "close" && this.BLTH) {
				var _ = this.BLTH.contentWindow.CloseWindow("close");
				if(_ === false) C.cancel = true
			}
		} catch(A) {}
		if(C.cancel == true) return C;
		this.fire("buttonclick", C);
		if(C.name == "close") if(this[Xfv] == "destroy") {
			this.__HideAction = "close";
			this[EqU5]()
		} else this.hide();
		if(C.name == "collapse") {
			this.toggle();
			if(this[XUW] && this.expanded && this.url) this.reload()
		}
		return C
	},
	onButtonClick: function(_, $) {
		this.on("buttonclick", _, $)
	},
	JOG: function() {
		this.buttons = [];
		var _ = this.createButton({
			name: "close",
			cls: "mini-tools-close",
			visible: this[$KS]
		});
		this.buttons.push(_);
		var $ = this.createButton({
			name: "collapse",
			cls: "mini-tools-collapse",
			visible: this[CJP]
		});
		this.buttons.push($)
	},
	createButton: function(_) {
		var $ = mini.copyTo({
			name: "",
			cls: "",
			style: "",
			visible: true,
			enabled: true,
			html: ""
		}, _);
		return $
	},
	addButton: function(_, $) {
		if(typeof _ == "string") _ = {
			iconCls: _
		};
		_ = this.createButton(_);
		if(typeof $ != "number") $ = this.buttons.length;
		this.buttons.insert($, _);
		this[Mdk]()
	},
	updateButton: function($, A) {
		var _ = this.getButton($);
		if(!_) return;
		mini.copyTo(_, A);
		this[Mdk]()
	},
	removeButton: function($) {
		var _ = this.getButton($);
		if(!_) return;
		this.buttons.remove(_);
		this[Mdk]()
	},
	getButton: function($) {
		if(typeof $ == "number") return this.buttons[$];
		else for(var _ = 0, A = this.buttons.length; _ < A; _++) {
			var B = this.buttons[_];
			if(B.name == $) return B
		}
	},
	destroy: function($) {
		this.SUcw();
		this.BLTH = null;
		this.B4N = null;
		this.H23I = null;
		this.Yj = null;
		NpV8[GR_][EqU5][GkN](this, $)
	},
	setBody: function(_) {
		if(!_) return;
		if(!mini.isArray(_)) _ = [_];
		for(var $ = 0, A = _.length; $ < A; $++) {
			var B = _[$];
			mini.append(this.H23I, B)
		}
		mini.parse(this.H23I);
		this[Fcv]()
	},
	set_bodyParent: function($) {},
	setToolbar: function(_) {
		if(!_) return;
		if(!mini.isArray(_)) _ = [_];
		for(var $ = 0, A = _.length; $ < A; $++) mini.append(this.B4N, _[$]);
		mini.parse(this.B4N);
		this[Fcv]()
	},
	setFooter: function(_) {
		if(!_) return;
		if(!mini.isArray(_)) _ = [_];
		for(var $ = 0, A = _.length; $ < A; $++) mini.append(this.Yj, _[$]);
		mini.parse(this.Yj);
		this[Fcv]()
	},
	getHeaderEl: function() {
		return this.NL
	},
	getToolbarEl: function() {
		return this.B4N
	},
	getBodyEl: function() {
		return this.H23I
	},
	getFooterEl: function() {
		return this.Yj
	},
	getIFrameEl: function($) {
		return this.BLTH
	},
	Qcra: function() {
		return this.H23I
	},
	SUcw: function($) {
		if(this.BLTH && this.BLTH.parentNode) {
			this.BLTH._ondestroy();
			this.BLTH.parentNode.removeChild(this.BLTH);
			try {
				this.BLTH[BnX](true)
			} catch(_) {}
		}
		this.BLTH = null;
		try {
			CollectGarbage()
		} catch(_) {}
		if($ === true) mini.removeChilds(this.H23I)
	},
	Izz: 80,
	YqFk: function() {
		this.SUcw(true);
		var A = new Date(),
			$ = this;
		this.loadedUrl = this.url;
		if(this.maskOnLoad) this.loading();
		var _ = mini.createIFrame(this.url, function(_, C) {
			var B = (A - new Date()) + $.Izz;
			if(B < 0) B = 0;
			setTimeout(function() {
				$.unmask()
			}, B);
			try {
				$.BLTH.contentWindow.Owner = $.Owner;
				$.BLTH.contentWindow.CloseOwnerWindow = function(_) {
					setTimeout(function() {
						$.__HideAction = _;
						$[EqU5]()
					}, 1)
				}
			} catch(D) {}
			if($.__onLoad) $.__onLoad();
			var D = {
				iframe: $.BLTH
			};
			$.fire("load", D)
		}, function() {
			try {
				if($.__onDestroy) $.__onDestroy($.__HideAction)
			} catch(_) {
				throw _
			} finally {
				var _ = {
					iframe: $.BLTH
				};
				$.fire("unload", _)
			}
		});
		this.H23I.appendChild(_);
		this.BLTH = _
	},
	load: function(_, $, A) {
		this.setUrl(_, $, A)
	},
	reload: function() {
		this.setUrl(this.url)
	},
	setUrl: function($, _, A) {
		this.url = $;
		this.__onLoad = _;
		this.__onDestroy = A;
		if(this.expanded) this.YqFk()
	},
	getUrl: function() {
		return this.url
	},
	setRefreshOnExpand: function($) {
		this[XUW] = $
	},
	getRefreshOnExpand: function() {
		return this[XUW]
	},
	setMaskOnLoad: function($) {
		this.maskOnLoad = $
	},
	getMaskOnLoad: function($) {
		return this.maskOnLoad
	},
	expanded: true,
	setExpanded: function($) {
		if(this.expanded != $) {
			this.expanded = $;
			if(this.expanded) this.expand();
			else this.collapse()
		}
	},
	toggle: function() {
		if(this.expanded) this.collapse();
		else this.expand()
	},
	collapse: function() {
		this.expanded = false;
		this._height = this.el.style.height;
		this.el.style.height = "auto";
		this.OgC.style.display = "none";
		V7(this.el, "mini-panel-collapse");
		this[Fcv]()
	},
	expand: function() {
		this.expanded = true;
		this.el.style.height = this._height;
		this.OgC.style.display = "block";
		delete this._height;
		HMT(this.el, "mini-panel-collapse");
		if(this.url && this.url != this.loadedUrl) this.YqFk();
		this[Fcv]()
	},
	getAttrs: function(_) {
		var D = NpV8[GR_][$gN][GkN](this, _);
		mini[ENl](_, D, ["title", "iconCls", "iconStyle", "headerCls", "headerStyle", "bodyCls", "bodyStyle", "footerCls", "footerStyle", "toolbarCls", "toolbarStyle", "footer", "toolbar", "url", "closeAction", "loadingMsg"]);
		mini[XD9s](_, D, ["allowResize", "showCloseButton", "showHeader", "showToolbar", "showFooter", "showCollapseButton", "refreshOnExpand", "maskOnLoad", "expanded"]);
		var C = mini[OIAh](_, true);
		for(var $ = C.length - 1; $ >= 0; $--) {
			var B = C[$],
				A = jQuery(B).attr("property");
			if(!A) continue;
			A = A.toLowerCase();
			if(A == "toolbar") D.toolbar = B;
			else if(A == "footer") D.footer = B
		}
		D.body = C;
		return D
	}
});
PC7(NpV8, "panel");
EzY = function() {
	EzY[GR_][KNT][GkN](this);
	this[QlR]("mini-window");
	this[YKu](false);
	this.setAllowDrag(this.allowDrag);
	this.setAllowResize(this[JkX])
};
Iov(EzY, NpV8, {
	x: 0,
	y: 0,
	state: "restore",
	LM6P: "mini-window-drag",
	Tey: "mini-window-resize",
	allowDrag: true,
	allowResize: false,
	showCloseButton: true,
	showMaxButton: false,
	showMinButton: false,
	showCollapseButton: false,
	showModal: true,
	minWidth: 150,
	minHeight: 80,
	maxWidth: 2000,
	maxHeight: 2000,
	uiCls: "mini-window",
	_create: function() {
		EzY[GR_][Sir][GkN](this)
	},
	JOG: function() {
		this.buttons = [];
		var A = this.createButton({
			name: "close",
			cls: "mini-tools-close",
			visible: this[$KS]
		});
		this.buttons.push(A);
		var B = this.createButton({
			name: "max",
			cls: "mini-tools-max",
			visible: this[XS]
		});
		this.buttons.push(B);
		var _ = this.createButton({
			name: "min",
			cls: "mini-tools-min",
			visible: this[Rhc]
		});
		this.buttons.push(_);
		var $ = this.createButton({
			name: "collapse",
			cls: "mini-tools-collapse",
			visible: this[CJP]
		});
		this.buttons.push($)
	},
	_initEvents: function() {
		EzY[GR_][S1B][GkN](this);
		IZY2(function() {
			$v9(this.el, "mouseover", this.LZR, this);
			$v9(window, "resize", this.SA6y, this);
			$v9(this.el, "mousedown", this.$aSK, this)
		}, this)
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		if(this.state == "max") {
			var $ = this.getParentBox();
			this.el.style.left = "0px";
			this.el.style.top = "0px";
			mini.setSize(this.el, $.width, $.height)
		}
		EzY[GR_][Fcv][GkN](this);
		if(this.allowDrag) V7(this.el, this.LM6P);
		if(this.state == "max") {
			this.Vhh.style.display = "none";
			HMT(this.el, this.LM6P)
		}
		this.$X9()
	},
	$X9: function() {
		var _ = this[IoF] && this[WH4]();
		if(!this.NsDe) this.NsDe = mini.append(document.body, "<div class=\"mini-modal\" style=\"display:none\"></div>");
		this.NsDe.style.display = _ ? "block" : "none";
		if(_) {
			var $ = mini.getViewportBox();
			this.NsDe.style.height = $.height + "px";
			this.NsDe.style.width = "100%";
			this.NsDe.style.zIndex = FrU(this.el, "zIndex") - 1
		}
	},
	getParentBox: function() {
		var $ = mini.getViewportBox(),
			_ = this.SyAJ || document.body;
		if(_ != document.body) $ = Y3L(_);
		return $
	},
	setShowModal: function($) {
		this[IoF] = $
	},
	getShowModal: function() {
		return this[IoF]
	},
	setMinWidth: function($) {
		if(isNaN($)) return;
		this.minWidth = $
	},
	getMinWidth: function() {
		return this.minWidth
	},
	setMinHeight: function($) {
		if(isNaN($)) return;
		this.minHeight = $
	},
	getMinHeight: function() {
		return this.minHeight
	},
	setMaxWidth: function($) {
		if(isNaN($)) return;
		this.maxWidth = $
	},
	getMaxWidth: function() {
		return this.maxWidth
	},
	setMaxHeight: function($) {
		if(isNaN($)) return;
		this.maxHeight = $
	},
	getMaxHeight: function() {
		return this.maxHeight
	},
	setAllowDrag: function($) {
		this.allowDrag = $;
		HMT(this.el, this.LM6P);
		if($) V7(this.el, this.LM6P)
	},
	getAllowDrag: function() {
		return this.allowDrag
	},
	setAllowResize: function($) {
		if(this[JkX] != $) {
			this[JkX] = $;
			this[Fcv]()
		}
	},
	getAllowResize: function() {
		return this[JkX]
	},
	setShowMaxButton: function($) {
		this[XS] = $;
		var _ = this.getButton("max");
		_.visible = $;
		if(_) this[Mdk]()
	},
	getShowMaxButton: function() {
		return this[XS]
	},
	setShowMinButton: function($) {
		this[Rhc] = $;
		var _ = this.getButton("min");
		_.visible = $;
		if(_) this[Mdk]()
	},
	getShowMinButton: function() {
		return this[Rhc]
	},
	max: function() {
		this.state = "max";
		this.show();
		var $ = this.getButton("max");
		if($) {
			$.cls = "mini-tools-restore";
			this[Mdk]()
		}
	},
	restore: function() {
		this.state = "restore";
		this.show(this.x, this.y);
		var $ = this.getButton("max");
		if($) {
			$.cls = "mini-tools-max";
			this[Mdk]()
		}
	},
	containerEl: null,
	show: function(B, _) {
		this.ERW = false;
		var A = this.SyAJ || document.body;
		if(!this.isRender() || this.el.parentNode != A) this[Ivp](A);
		this.el.style.zIndex = mini.getMaxZIndex();
		this.VNHk(B, _);
		this.ERW = true;
		this[YKu](true);
		if(this.state != "max") {
			var $ = Y3L(this.el);
			this.x = $.x;
			this.y = $.y
		}
	},
	hide: function() {
		this[YKu](false);
		this.$X9()
	},
	CLs: function() {
		this.el.style.display = "";
		var $ = Y3L(this.el);
		if($.width > this.maxWidth) {
			Sbkj(this.el, this.maxWidth);
			$ = Y3L(this.el)
		}
		if($.height > this.maxHeight) {
			SmU(this.el, this.maxHeight);
			$ = Y3L(this.el)
		}
		if($.width < this.minWidth) {
			Sbkj(this.el, this.minWidth);
			$ = Y3L(this.el)
		}
		if($.height < this.minHeight) {
			SmU(this.el, this.minHeight);
			$ = Y3L(this.el)
		}
	},
	VNHk: function(B, A) {
		var _ = this.getParentBox();
		if(this.state == "max") {
			if(!this._width) {
				var $ = Y3L(this.el);
				this._width = $.width;
				this._height = $.height;
				this.x = $.x;
				this.y = $.y
			}
		} else {
			if(mini.isNull(B)) B = "center";
			if(mini.isNull(A)) A = "middle";
			this.el.style.position = "absolute";
			this.el.style.left = "-2000px";
			this.el.style.top = "-2000px";
			this.el.style.display = "";
			if(this._width) {
				this[_wJ](this._width);
				this[P4](this._height)
			}
			this.CLs();
			$ = Y3L(this.el);
			if(B == "left") B = 0;
			if(B == "center") B = _.width / 2 - $.width / 2;
			if(B == "right") B = _.width - $.width;
			if(A == "top") A = 0;
			if(A == "middle") A = _.y + _.height / 2 - $.height / 2;
			if(A == "bottom") A = _.height - $.height;
			if(B + $.width > _.right) B = _.right - $.width;
			if(A + $.height > _.bottom) A = _.bottom - $.height;
			if(B < 0) B = 0;
			if(A < 0) A = 0;
			this.el.style.display = "";
			mini.setX(this.el, B);
			mini.setY(this.el, A)
		}
		this[Fcv]()
	},
	Ii: function(_, $) {
		var A = EzY[GR_].Ii[GkN](this, _, $);
		if(A.cancel == true) return A;
		if(A.name == "max") if(this.state == "max") this.restore();
		else this.max();
		return A
	},
	SA6y: function($) {
		if(this.state == "max") this[Fcv]()
	},
	$aSK: function(B) {
		var _ = this;
		if(this.state != "max" && this.allowDrag && TWAc(this.NL, B.target) && !ZW(B.target, "mini-tools")) {
			var _ = this,
				A = this.getBox(),
				$ = new mini.Drag({
					capture: false,
					onStart: function() {
						_.$UZs = mini.append(document.body, "<div class=\"mini-resizer-mask\"></div>");
						_.JUge = mini.append(document.body, "<div class=\"mini-drag-proxy\"></div>")
					},
					onMove: function(B) {
						var F = B.now[0] - B.init[0],
							E = B.now[1] - B.init[1];
						F = A.x + F;
						E = A.y + E;
						var D = _.getParentBox(),
							$ = F + A.width,
							C = E + A.height;
						if($ > D.width) F = D.width - A.width;
						if(C > D.bottom) E = D.bottom - A.height;
						if(F < 0) F = 0;
						if(E < 0) E = 0;
						_.x = F;
						_.y = E;
						var G = {
							x: F,
							y: E,
							width: A.width,
							height: A.height
						};
						$vA(_.JUge, G)
					},
					onStop: function() {
						var $ = Y3L(_.JUge);
						$vA(_.el, $);
						jQuery(_.$UZs).remove();
						_.$UZs = null;
						jQuery(_.JUge).remove();
						_.JUge = null
					}
				});
			$.start(B)
		}
		if(TWAc(this.Vhh, B.target) && this[JkX]) {
			$ = this.KCmB();
			$.start(B)
		}
	},
	KCmB: function() {
		if(!this._resizeDragger) this._resizeDragger = new mini.Drag({
			capture: true,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this._resizeDragger
	},
	Jb: function($) {
		this.proxy = mini.append(document.body, "<div class=\"mini-windiw-resizeProxy\"></div>");
		this.proxy.style.cursor = "se-resize";
		this.elBox = Y3L(this.el);
		$vA(this.proxy, this.elBox)
	},
	Pc4: function(A) {
		var C = A.now[0] - A.init[0],
			$ = A.now[1] - A.init[1],
			_ = this.elBox.width + C,
			B = this.elBox.height + $;
		if(_ < this.minWidth) _ = this.minWidth;
		if(B < this.minHeight) B = this.minHeight;
		if(_ > this.maxWidth) _ = this.maxWidth;
		if(B > this.maxHeight) B = this.maxHeight;
		mini.setSize(this.proxy, _, B)
	},
	IzO: function($) {
		var _ = Y3L(this.proxy);
		jQuery(this.proxy).remove();
		this.proxy = null;
		this.elBox = null;
		this[_wJ](_.width);
		this[P4](_.height);
		delete this._width;
		delete this._height
	},
	destroy: function($) {
		M4(window, "resize", this.SA6y, this);
		if(this.NsDe) {
			jQuery(this.NsDe).remove();
			this.NsDe = null
		}
		if(this.shadowEl) {
			jQuery(this.shadowEl).remove();
			this.shadowEl = null
		}
		EzY[GR_][EqU5][GkN](this, $)
	},
	getAttrs: function($) {
		var _ = EzY[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["modalStyle"]);
		mini[XD9s]($, _, ["showModal", "showShadow", "allowDrag", "allowResize", "showMaxButton", "showMinButton"]);
		mini[GGt]($, _, ["minWidth", "minHeight", "maxWidth", "maxHeight"]);
		return _
	}
});
PC7(EzY, "window");
mini.MessageBox = {
	alertTitle: "\u63d0\u9192",
	confirmTitle: "\u786e\u8ba4",
	prompTitle: "\u8f93\u5165",
	prompMessage: "\u8bf7\u8f93\u5165\u5185\u5bb9\uff1a",
	buttonText: {
		ok: "\u786e\u5b9a",
		cancel: "\u53d6\u6d88",
		yes: "\u662f",
		no: "\u5426"
	},
	show: function(F) {
		F = mini.copyTo({
			width: "auto",
			height: "auto",
			minWidth: 150,
			maxWidth: 800,
			minHeight: 100,
			maxHeight: 350,
			title: "",
			titleIcon: "",
			iconCls: "",
			iconStyle: "",
			message: "",
			html: "",
			spaceStyle: "margin-right:15px",
			showCloseButton: true,
			buttons: null,
			buttonWidth: 55,
			callback: null
		}, F);
		var I = F.callback,
			C = new EzY();
		C.setBodyStyle("overflow:hidden");
		C.setShowModal(true);
		C.setTitle(F.title || "");
		C.setIconCls(F.titleIcon);
		C.setShowCloseButton(F[$KS]);
		var J = C.uid + "$table",
			N = C.uid + "$content",
			L = "<div class=\"" + F.iconCls + "\" style=\"" + F[DP] + "\"></div>",
			Q = "<table class=\"mini-messagebox-table\" id=\"" + J + "\" style=\"\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>" + L + "</td><td id=\"" + N + "\" style=\"text-align:center;padding:8px;padding-left:0;\">" + (F.message || "") + "</td></tr></table>",
			_ = "<div class=\"mini-messagebox-content\"></div>" + "<div class=\"mini-messagebox-buttons\"></div>";
		C.H23I.innerHTML = _;
		var M = C.H23I.firstChild;
		if(F.html) {
			if(typeof F.html == "string") M.innerHTML = F.html;
			else if(mini.isElement(F.html)) M.appendChild(F.html)
		} else M.innerHTML = Q;
		C._Buttons = [];
		var P = C.H23I.lastChild;
		if(F.buttons && F.buttons.length > 0) {
			for(var H = 0, D = F.buttons.length; H < D; H++) {
				var E = F.buttons[H],
					K = mini.MessageBox.buttonText[E],
					$ = new Has6();
				$[Chg](K);
				$[_wJ](F.buttonWidth);
				$[Ivp](P);
				$.action = E;
				$.on("click", function(_) {
					var $ = _.sender;
					if(I) I($.action);
					mini.MessageBox.hide(C)
				});
				if(H != D - 1) $.setStyle(F.spaceStyle);
				C._Buttons.push($)
			}
		} else P.style.display = "none";
		C.setMinWidth(F.minWidth);
		C.setMinHeight(F.minHeight);
		C.setMaxWidth(F.maxWidth);
		C.setMaxHeight(F.maxHeight);
		C[_wJ](F.width);
		C[P4](F.height);
		C.show();
		var A = C[Y1Q]();
		C[_wJ](A);
		var B = document.getElementById(J);
		if(B) B.style.width = "100%";
		var G = document.getElementById(N);
		if(G) G.style.width = "100%";
		var O = C._Buttons[0];
		if(O) O.focus();
		else C.focus();
		C.on("beforebuttonclick", function($) {
			if(I) I("close");
			$.cancel = true;
			mini.MessageBox.hide(C)
		});
		$v9(C.el, "keydown", function($) {
			if($.keyCode == 27) {
				if(I) I("close");
				$.cancel = true;
				mini.MessageBox.hide(C)
			}
		});
		return C.uid
	},
	hide: function(C) {
		if(!C) return;
		var _ = typeof C == "object" ? C : mini.getbyUID(C);
		if(!_) return;
		for(var $ = 0, A = _._Buttons.length; $ < A; $++) {
			var B = _._Buttons[$];
			B[EqU5]()
		}
		_._Buttons = null;
		_[EqU5]()
	},
	alert: function(A, _, $) {
		return mini.MessageBox.show({
			minWidth: 250,
			title: _ || mini.MessageBox.alertTitle,
			buttons: ["ok"],
			message: A,
			iconCls: "mini-messagebox-warning",
			callback: $
		})
	},
	confirm: function(A, _, $) {
		return mini.MessageBox.show({
			minWidth: 250,
			title: _ || mini.MessageBox.confirmTitle,
			buttons: ["ok", "cancel"],
			message: A,
			iconCls: "mini-messagebox-question",
			callback: $
		})
	},
	prompt: function(C, B, A, _) {
		var F = "prompt$" + new Date().getTime(),
			E = C || mini.MessageBox.promptMessage;
		if(_) E = E + "<br/><textarea id=\"" + F + "\" style=\"width:200px;height:60px;margin-top:3px;\"></textarea>";
		else E = E + "<br/><input id=\"" + F + "\" type=\"text\" style=\"width:200px;margin-top:3px;\"/>";
		var D = mini.MessageBox.show({
			title: B || mini.MessageBox.promptTitle,
			buttons: ["ok", "cancel"],
			width: 250,
			html: "<div style=\"padding:5px;padding-left:10px;\">" + E + "</div>",
			callback: function(_) {
				var $ = document.getElementById(F);
				if(A) A(_, $.value)
			}
		}),
			$ = document.getElementById(F);
		$.focus();
		return D
	},
	loading: function(_, $) {
		return mini.MessageBox.show({
			minHeight: 50,
			title: $,
			showCloseButton: false,
			message: _,
			iconCls: "mini-messagebox-waiting"
		})
	}
};
mini.alert = mini.MessageBox.alert;
mini.confirm = mini.MessageBox.confirm;
mini.prompt = mini.MessageBox.prompt;
mini.loading = mini.MessageBox.loading;
mini.showMessageBox = mini.MessageBox.show;
mini.hideMessageBox = mini.MessageBox.hide;
XQ = function() {
	this.E1B();
	XQ[GR_][KNT][GkN](this)
};
Iov(XQ, FIa, {
	width: 300,
	height: 180,
	vertical: false,
	allowResize: true,
	pane1: null,
	pane2: null,
	showHandleButton: true,
	handlerStyle: "",
	handlerCls: "",
	handlerSize: 6,
	uiCls: "mini-splitter",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-splitter";
		this.el.innerHTML = "<div class=\"mini-splitter-border\"><div id=\"1\" class=\"mini-splitter-pane mini-splitter-pane1\"></div><div id=\"2\" class=\"mini-splitter-pane mini-splitter-pane2\"></div><div class=\"mini-splitter-handler\"></div></div>";
		this.TG = this.el.firstChild;
		this.Hbo = this.TG.firstChild;
		this.JRA = this.TG.childNodes[1];
		this.E9y = this.TG.lastChild
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this);
			$v9(this.el, "mousedown", this.AOlf, this)
		}, this)
	},
	E1B: function() {
		this.pane1 = {
			index: 1,
			minSize: 30,
			maxSize: 3000,
			size: "",
			showCollapseButton: false,
			cls: "",
			style: "",
			visible: true,
			expanded: true
		};
		this.pane2 = mini.copyTo({}, this.pane1);
		this.pane2.index = 2
	},
	doUpdate: function() {
		this[Fcv]()
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		this.E9y.style.cursor = this[JkX] ? "" : "default";
		HMT(this.el, "mini-splitter-vertical");
		if(this.vertical) V7(this.el, "mini-splitter-vertical");
		HMT(this.Hbo, "mini-splitter-pane1-vertical");
		HMT(this.JRA, "mini-splitter-pane2-vertical");
		if(this.vertical) {
			V7(this.Hbo, "mini-splitter-pane1-vertical");
			V7(this.JRA, "mini-splitter-pane2-vertical")
		}
		HMT(this.E9y, "mini-splitter-handler-vertical");
		if(this.vertical) V7(this.E9y, "mini-splitter-handler-vertical");
		Gn(this.Hbo, this.pane1.style);
		Gn(this.JRA, this.pane2.style);
		var B = this[DTTy](true),
			_ = this[Y1Q](true);
		if(!jQuery.boxModel) {
			var Q = D0uu(this.TG);
			B = B + Q.top + Q.bottom;
			_ = _ + Q.left + Q.right
		}
		this.TG.style.width = _ + "px";
		this.TG.style.height = B + "px";
		var $ = this.Hbo,
			C = this.JRA,
			G = jQuery($),
			I = jQuery(C);
		$.style.display = C.style.display = this.E9y.style.display = "";
		var D = this[Jgq];
		this.pane1.size = String(this.pane1.size);
		this.pane2.size = String(this.pane2.size);
		var F = parseFloat(this.pane1.size),
			H = parseFloat(this.pane2.size),
			O = isNaN(F),
			T = isNaN(H),
			N = !isNaN(F) && this.pane1.size.indexOf("%") != -1,
			R = !isNaN(H) && this.pane2.size.indexOf("%") != -1,
			J = !O && !N,
			M = !T && !R,
			P = this.vertical ? B - this[Jgq] : _ - this[Jgq],
			K = p2Size = 0;
		if(O || T) {
			if(O && T) {
				K = parseInt(P / 2);
				p2Size = P - K
			} else if(J) {
				K = F;
				p2Size = P - K
			} else if(N) {
				K = parseInt(P * F / 100);
				p2Size = P - K
			} else if(M) {
				p2Size = H;
				K = P - p2Size
			} else if(R) {
				p2Size = parseInt(P * H / 100);
				K = P - p2Size
			}
		} else if(N && M) {
			p2Size = H;
			K = P - p2Size
		} else if(J && R) {
			K = F;
			p2Size = P - K
		} else {
			var L = F + H;
			K = parseInt(P * F / L);
			p2Size = P - K
		}
		if(K > this.pane1.maxSize) {
			K = this.pane1.maxSize;
			p2Size = P - K
		}
		if(p2Size > this.pane2.maxSize) {
			p2Size = this.pane2.maxSize;
			K = P - p2Size
		}
		if(K < this.pane1.minSize) {
			K = this.pane1.minSize;
			p2Size = P - K
		}
		if(p2Size < this.pane2.minSize) {
			p2Size = this.pane2.minSize;
			K = P - p2Size
		}
		if(this.pane1.expanded == false) {
			p2Size = P;
			K = 0;
			$.style.display = "none"
		} else if(this.pane2.expanded == false) {
			K = P;
			p2Size = 0;
			C.style.display = "none"
		}
		if(this.pane1.visible == false) {
			p2Size = P + D;
			K = D = 0;
			$.style.display = "none";
			this.E9y.style.display = "none"
		} else if(this.pane2.visible == false) {
			K = P + D;
			p2Size = D = 0;
			C.style.display = "none";
			this.E9y.style.display = "none"
		}
		if(this.vertical) {
			Sbkj($, _);
			Sbkj(C, _);
			SmU($, K);
			SmU(C, p2Size);
			C.style.top = (K + D) + "px";
			this.E9y.style.left = "0px";
			this.E9y.style.top = K + "px";
			Sbkj(this.E9y, _);
			SmU(this.E9y, this[Jgq]);
			$.style.left = "0px";
			C.style.left = "0px"
		} else {
			Sbkj($, K);
			Sbkj(C, p2Size);
			SmU($, B);
			SmU(C, B);
			C.style.left = (K + D) + "px";
			this.E9y.style.top = "0px";
			this.E9y.style.left = K + "px";
			Sbkj(this.E9y, this[Jgq]);
			SmU(this.E9y, B);
			$.style.top = "0px";
			C.style.top = "0px"
		}
		var S = "<div class=\"mini-splitter-handler-buttons\">";
		if(!this.pane1.expanded || !this.pane2.expanded) {
			if(!this.pane1.expanded) {
				if(this.pane1[CJP]) S += "<a id=\"1\" class=\"mini-splitter-pane2-button\"></a>"
			} else if(this.pane2[CJP]) S += "<a id=\"2\" class=\"mini-splitter-pane1-button\"></a>"
		} else {
			if(this.pane1[CJP]) S += "<a id=\"1\" class=\"mini-splitter-pane1-button\"></a>";
			if(this[JkX]) if((this.pane1[CJP] && this.pane2[CJP]) || (!this.pane1[CJP] && !this.pane2[CJP])) S += "<span class=\"mini-splitter-resize-button\"></span>";
			if(this.pane2[CJP]) S += "<a id=\"2\" class=\"mini-splitter-pane2-button\"></a>"
		}
		S += "</div>";
		this.E9y.innerHTML = S;
		var E = this.E9y.firstChild;
		E.style.display = this.showHandleButton ? "" : "none";
		var A = Y3L(E);
		if(this.vertical) E.style.marginLeft = -A.width / 2 + "px";
		else E.style.marginTop = -A.height / 2 + "px";
		if(!this.pane1.visible || !this.pane2.visible || !this.pane1.expanded || !this.pane2.expanded) V7(this.E9y, "mini-splitter-nodrag");
		else HMT(this.E9y, "mini-splitter-nodrag");
		mini.layout(this.TG)
	},
	getPaneBox: function($) {
		var _ = this.getPaneEl($);
		if(!_) return null;
		return Y3L(_)
	},
	getPane: function($) {
		if($ == 1) return this.pane1;
		else if($ == 2) return this.pane2;
		return $
	},
	setPanes: function(_) {
		if(!mini.isArray(_)) return;
		for(var $ = 0; $ < 2; $++) {
			var A = _[$];
			this.updatePane($ + 1, A)
		}
	},
	getPaneEl: function($) {
		if($ == 1) return this.Hbo;
		return this.JRA
	},
	updatePane: function(_, F) {
		var $ = this.getPane(_);
		if(!$) return;
		mini.copyTo($, F);
		var B = this.getPaneEl(_),
			C = $.body;
		delete $.body;
		if(C) {
			if(!mini.isArray(C)) C = [C];
			for(var A = 0, E = C.length; A < E; A++) mini.append(B, C[A])
		}
		if($.bodyParent) {
			var D = $.bodyParent;
			while(D.firstChild) B.appendChild(D.firstChild)
		}
		delete $.bodyParent;
		this[Mdk]()
	},
	setShowHandleButton: function($) {
		this.showHandleButton = $;
		this[Mdk]()
	},
	getShowHandleButton: function($) {
		return this.showHandleButton
	},
	setVertical: function($) {
		this.vertical = $;
		this[Mdk]()
	},
	getVertical: function() {
		return this.vertical
	},
	expandPane: function(_) {
		var $ = this.getPane(_);
		if(!$) return;
		$.expanded = true;
		this[Mdk]()
	},
	collapsePane: function(_) {
		var $ = this.getPane(_);
		if(!$) return;
		$.expanded = false;
		var A = $ == this.pane1 ? this.pane2 : this.pane1;
		if(A.expanded == false) {
			A.expanded = true;
			A.visible = true
		}
		this[Mdk]()
	},
	togglePane: function(_) {
		var $ = this.getPane(_);
		if(!$) return;
		if($.expanded) this.collapsePane($);
		else this.expandPane($)
	},
	showPane: function(_) {
		var $ = this.getPane(_);
		if(!$) return;
		$.visible = true;
		this[Mdk]()
	},
	hidePane: function(_) {
		var $ = this.getPane(_);
		if(!$) return;
		$.visible = false;
		var A = $ == this.pane1 ? this.pane2 : this.pane1;
		if(A.visible == false) {
			A.expanded = true;
			A.visible = true
		}
		this[Mdk]()
	},
	setAllowResize: function($) {
		if(this[JkX] != $) {
			this[JkX] = $;
			this[Fcv]()
		}
	},
	getAllowResize: function() {
		return this[JkX]
	},
	setHandlerSize: function($) {
		if(this[Jgq] != $) {
			this[Jgq] = $;
			this[Fcv]()
		}
	},
	getHandlerSize: function() {
		return this[Jgq]
	},
	PGY: function(B) {
		var A = B.target;
		if(!TWAc(this.E9y, A)) return;
		var _ = parseInt(A.id),
			$ = this.getPane(_),
			B = {
				pane: $,
				paneIndex: _,
				cancel: false
			};
		if($.expanded) this.fire("beforecollapse", B);
		else this.fire("beforeexpand", B);
		if(B.cancel == true) return;
		if(A.className == "mini-splitter-pane1-button") this.togglePane(_);
		else if(A.className == "mini-splitter-pane2-button") this.togglePane(_)
	},
	Ii: function($, _) {
		this.fire("buttonclick", {
			pane: $,
			index: this.pane1 == $ ? 1 : 2,
			htmlEvent: _
		})
	},
	onButtonClick: function(_, $) {
		this.on("buttonclick", _, $)
	},
	AOlf: function(A) {
		var _ = A.target;
		if(!this[JkX]) return;
		if(!this.pane1.visible || !this.pane2.visible || !this.pane1.expanded || !this.pane2.expanded) return;
		if(TWAc(this.E9y, _)) if(_.className == "mini-splitter-pane1-button" || _.className == "mini-splitter-pane2-button");
		else {
			var $ = this.IN4();
			$.start(A)
		}
	},
	IN4: function() {
		if(!this.drag) this.drag = new mini.Drag({
			capture: true,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this.drag
	},
	Jb: function($) {
		this.$UZs = mini.append(document.body, "<div class=\"mini-resizer-mask\"></div>");
		this.JUge = mini.append(document.body, "<div class=\"mini-proxy\"></div>");
		this.JUge.style.cursor = this.vertical ? "n-resize" : "w-resize";
		this.handlerBox = Y3L(this.E9y);
		this.elBox = Y3L(this.TG, true);
		$vA(this.JUge, this.handlerBox)
	},
	Pc4: function(C) {
		if(!this.handlerBox) return;
		if(!this.elBox) this.elBox = Y3L(this.TG, true);
		var B = this.elBox.width,
			D = this.elBox.height,
			E = this[Jgq],
			I = this.vertical ? D - this[Jgq] : B - this[Jgq],
			A = this.pane1.minSize,
			F = this.pane1.maxSize,
			$ = this.pane2.minSize,
			G = this.pane2.maxSize;
		if(this.vertical == true) {
			var _ = C.now[1] - C.init[1],
				H = this.handlerBox.y + _;
			if(H - this.elBox.y > F) H = this.elBox.y + F;
			if(H + this.handlerBox.height < this.elBox.bottom - G) H = this.elBox.bottom - G - this.handlerBox.height;
			if(H - this.elBox.y < A) H = this.elBox.y + A;
			if(H + this.handlerBox.height > this.elBox.bottom - $) H = this.elBox.bottom - $ - this.handlerBox.height;
			mini.setY(this.JUge, H)
		} else {
			var J = C.now[0] - C.init[0],
				K = this.handlerBox.x + J;
			if(K - this.elBox.x > F) K = this.elBox.x + F;
			if(K + this.handlerBox.width < this.elBox.right - G) K = this.elBox.right - G - this.handlerBox.width;
			if(K - this.elBox.x < A) K = this.elBox.x + A;
			if(K + this.handlerBox.width > this.elBox.right - $) K = this.elBox.right - $ - this.handlerBox.width;
			mini.setX(this.JUge, K)
		}
	},
	IzO: function(_) {
		var $ = this.elBox.width,
			B = this.elBox.height,
			C = this[Jgq],
			D = parseFloat(this.pane1.size),
			E = parseFloat(this.pane2.size),
			I = isNaN(D),
			N = isNaN(E),
			J = !isNaN(D) && this.pane1.size.indexOf("%") != -1,
			M = !isNaN(E) && this.pane2.size.indexOf("%") != -1,
			G = !I && !J,
			K = !N && !M,
			L = this.vertical ? B - this[Jgq] : $ - this[Jgq],
			A = Y3L(this.JUge),
			H = A.x - this.elBox.x,
			F = L - H;
		if(this.vertical) {
			H = A.y - this.elBox.y;
			F = L - H
		}
		if(I || N) {
			if(I && N) {
				D = parseFloat(H / L * 100).toFixed(1);
				this.pane1.size = D + "%"
			} else if(G) {
				D = H;
				this.pane1.size = D
			} else if(J) {
				D = parseFloat(H / L * 100).toFixed(1);
				this.pane1.size = D + "%"
			} else if(K) {
				E = F;
				this.pane2.size = E
			} else if(M) {
				E = parseFloat(F / L * 100).toFixed(1);
				this.pane2.size = E + "%"
			}
		} else if(J && K) this.pane2.size = F;
		else if(G && M) this.pane1.size = H;
		else {
			this.pane1.size = parseFloat(H / L * 100).toFixed(1);
			this.pane2.size = 100 - this.pane1.size
		}
		jQuery(this.JUge).remove();
		jQuery(this.$UZs).remove();
		this.$UZs = null;
		this.JUge = null;
		this.elBox = this.handlerBox = null;
		this[Fcv]()
	},
	getAttrs: function(B) {
		var G = XQ[GR_][$gN][GkN](this, B);
		mini[XD9s](B, G, ["allowResize", "vertical", "showHandleButton"]);
		mini[GGt](B, G, ["handlerSize"]);
		var A = [],
			F = mini[OIAh](B);
		for(var _ = 0, E = 2; _ < E; _++) {
			var C = F[_],
				D = jQuery(C),
				$ = {};
			A.push($);
			if(!C) continue;
			$.style = C.style.cssText;
			mini[ENl](C, $, ["cls", "size"]);
			mini[XD9s](C, $, ["visible", "expanded", "showCollapseButton"]);
			mini[GGt](C, $, ["minSize", "maxSize", "handlerSize"]);
			$.bodyParent = C
		}
		G.panes = A;
		return G
	}
});
PC7(XQ, "splitter");
Gi2Z = function() {
	this.regions = [];
	this.regionMap = {};
	Gi2Z[GR_][KNT][GkN](this)
};
Iov(Gi2Z, FIa, {
	regions: [],
	splitSize: 6,
	collapseWidth: 28,
	collapseHeight: 25,
	regionWidth: 150,
	regionHeight: 80,
	regionMinWidth: 50,
	regionMinHeight: 25,
	regionMaxWidth: 2000,
	regionMaxHeight: 2000,
	uiCls: "mini-layout",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-layout";
		this.el.innerHTML = "<div class=\"mini-layout-border\"></div>";
		this.TG = this.el.firstChild;
		this[Mdk]()
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this);
			$v9(this.el, "mousedown", this.AOlf, this);
			$v9(this.el, "mouseover", this.LZR, this);
			$v9(this.el, "mouseout", this.$MHa, this);
			$v9(document, "mousedown", this.UgY, this)
		}, this)
	},
	getRegionEl: function($) {
		var $ = this[JN]($);
		if(!$) return null;
		return $._el
	},
	getRegionHeaderEl: function($) {
		var $ = this[JN]($);
		if(!$) return null;
		return $._header
	},
	getRegionBodyEl: function($) {
		var $ = this[JN]($);
		if(!$) return null;
		return $._body
	},
	getRegionSplitEl: function($) {
		var $ = this[JN]($);
		if(!$) return null;
		return $._split
	},
	getRegionProxyEl: function($) {
		var $ = this[JN]($);
		if(!$) return null;
		return $._proxy
	},
	getRegionBox: function(_) {
		var $ = this[$Tgc](_);
		if($) return Y3L($);
		return null
	},
	getRegion: function($) {
		if(typeof $ == "string") return this.regionMap[$];
		return $
	},
	Dpi: function(_, B) {
		var D = _.buttons;
		for(var $ = 0, A = D.length; $ < A; $++) {
			var C = D[$];
			if(C.name == B) return C
		}
	},
	XeHW: function(_) {
		var $ = mini.copyTo({
			region: "",
			title: "",
			iconCls: "",
			iconStyle: "",
			showCloseButton: false,
			showCollapseButton: true,
			buttons: [{
				name: "close",
				cls: "mini-tools-close",
				html: "",
				visible: false
			}, {
				name: "collapse",
				cls: "mini-tools-collapse",
				html: "",
				visible: true
			}],
			showSplit: true,
			showHeader: true,
			splitSize: this.splitSize,
			collapseSize: this.collapseWidth,
			width: this.regionWidth,
			height: this.regionHeight,
			minWidth: this.regionMinWidth,
			minHeight: this.regionMinHeight,
			maxWidth: this.regionMaxWidth,
			maxHeight: this.regionMaxHeight,
			allowResize: true,
			cls: "",
			style: "",
			headerCls: "",
			headerStyle: "",
			bodyCls: "",
			bodyStyle: "",
			visible: true,
			expanded: true
		}, _);
		return $
	},
	J5: function($) {
		var $ = this[JN]($);
		if(!$) return;
		mini.append(this.TG, "<div id=\"" + $.region + "\" class=\"mini-layout-region\"><div class=\"mini-layout-region-header\" style=\"" + $.headerStyle + "\"></div><div class=\"mini-layout-region-body\" style=\"" + $.bodyStyle + "\"></div></div>");
		$._el = this.TG.lastChild;
		$._header = $._el.firstChild;
		$._body = $._el.lastChild;
		if($.cls) V7($._el, $.cls);
		if($.style) Gn($._el, $.style);
		V7($._el, "mini-layout-region-" + $.region);
		if($.region != "center") {
			mini.append(this.TG, "<div uid=\"" + this.uid + "\" id=\"" + $.region + "\" class=\"mini-layout-split\"></div>");
			$._split = this.TG.lastChild;
			V7($._split, "mini-layout-split-" + $.region)
		}
		if($.region != "center") {
			mini.append(this.TG, "<div id=\"" + $.region + "\" class=\"mini-layout-proxy\"></div>");
			$._proxy = this.TG.lastChild;
			V7($._proxy, "mini-layout-proxy-" + $.region)
		}
	},
	setRegions: function(A) {
		if(!mini.isArray(A)) return;
		for(var $ = 0, _ = A.length; $ < _; $++) this.addRegion(A[$])
	},
	addRegion: function(D, $) {
		var G = D;
		D = this.XeHW(D);
		if(!D.region) D.region = "center";
		D.region = D.region.toLowerCase();
		if(D.region == "center" && G && !G.showHeader) D.showHeader = false;
		if(D.region == "north" || D.region == "south") if(!G.collapseSize) D.collapseSize = this.collapseHeight;
		this.WcQ(D);
		if(typeof $ != "number") $ = this.regions.length;
		var A = this.regionMap[D.region];
		if(A) return;
		this.regions.insert($, D);
		this.regionMap[D.region] = D;
		this.J5(D);
		var B = this.getRegionBodyEl(D),
			C = D.body;
		delete D.body;
		if(C) {
			if(!mini.isArray(C)) C = [C];
			for(var _ = 0, F = C.length; _ < F; _++) mini.append(B, C[_])
		}
		if(D.bodyParent) {
			var E = D.bodyParent;
			while(E.firstChild) B.appendChild(E.firstChild)
		}
		delete D.bodyParent;
		this[Mdk]()
	},
	removeRegion: function($) {
		var $ = this[JN]($);
		if(!$) return;
		this.regions.remove($);
		delete this.regionMap[$.region];
		jQuery($._el).remove();
		jQuery($._split).remove();
		jQuery($._proxy).remove();
		this[Mdk]()
	},
	moveRegion: function(A, $) {
		var A = this[JN](A);
		if(!A) return;
		var _ = this.regions[$];
		if(!_ || _ == A) return;
		this.regions.remove(A);
		var $ = this.region.indexOf(_);
		this.regions.insert($, A);
		this[Mdk]()
	},
	WcQ: function($) {
		var _ = this.Dpi($, "close");
		_.visible = $[$KS];
		_ = this.Dpi($, "collapse");
		_.visible = $[CJP];
		if($.width < $.minWidth) $.width = mini.minWidth;
		if($.width > $.maxWidth) $.width = mini.maxWidth;
		if($.height < $.minHeight) $.height = mini.minHeight;
		if($.height > $.maxHeight) $.height = mini.maxHeight
	},
	updateRegion: function($, _) {
		$ = this[JN]($);
		if(!$) return;
		if(_) delete _.region;
		mini.copyTo($, _);
		this.WcQ($);
		this[Mdk]()
	},
	expandRegion: function($) {
		$ = this[JN]($);
		if(!$) return;
		$.expanded = true;
		this[Mdk]()
	},
	collapseRegion: function($) {
		$ = this[JN]($);
		if(!$) return;
		$.expanded = false;
		this[Mdk]()
	},
	toggleRegion: function($) {
		$ = this[JN]($);
		if(!$) return;
		if($.expanded) this.collapseRegion($);
		else this.expandRegion($)
	},
	showRegion: function($) {
		$ = this[JN]($);
		if(!$) return;
		$.visible = true;
		this[Mdk]()
	},
	hideRegion: function($) {
		$ = this[JN]($);
		if(!$) return;
		$.visible = false;
		this[Mdk]()
	},
	isExpandRegion: function($) {
		$ = this[JN]($);
		if(!$) return null;
		return this.region.expanded
	},
	isVisibleRegion: function($) {
		$ = this[JN]($);
		if(!$) return null;
		return this.region.visible
	},
	TaWF: function($) {
		$ = this[JN]($);
		var _ = {
			region: $,
			cancel: false
		};
		if($.expanded) {
			this.fire("BeforeCollapse", _);
			if(_.cancel == false) this.collapseRegion($)
		} else {
			this.fire("BeforeExpand", _);
			if(_.cancel == false) this.expandRegion($)
		}
	},
	M0me: function(_) {
		var $ = ZW(_.target, "mini-layout-proxy");
		return $
	},
	KXl: function(_) {
		var $ = ZW(_.target, "mini-layout-region");
		return $
	},
	PGY: function(D) {
		if(this.Ms3) return;
		var A = this.M0me(D);
		if(A) {
			var _ = A.id,
				C = ZW(D.target, "mini-tools-collapse");
			if(C) this.TaWF(_);
			else this.Fesk(_)
		}
		var B = this.KXl(D);
		if(B && ZW(D.target, "mini-layout-region-header")) {
			_ = B.id, C = ZW(D.target, "mini-tools-collapse");
			if(C) this.TaWF(_);
			var $ = ZW(D.target, "mini-tools-close");
			if($) this.updateRegion(_, {
				visible: false
			})
		}
	},
	Ii: function(_, A, $) {
		this.fire("buttonclick", {
			htmlEvent: $,
			region: _,
			button: A,
			index: this.buttons.indexOf(A),
			name: A.name
		})
	},
	Swk: function(_, A, $) {
		this.fire("buttonmousedown", {
			htmlEvent: $,
			region: _,
			button: A,
			index: this.buttons.indexOf(A),
			name: A.name
		})
	},
	hoverProxyEl: null,
	LZR: function(_) {
		var $ = this.M0me(_);
		if($) {
			V7($, "mini-layout-proxy-hover");
			this.hoverProxyEl = $
		}
	},
	$MHa: function($) {
		if(this.hoverProxyEl) HMT(this.hoverProxyEl, "mini-layout-proxy-hover");
		this.hoverProxyEl = null
	},
	onButtonClick: function(_, $) {
		this.on("buttonclick", _, $)
	},
	onButtonMouseDown: function(_, $) {
		this.on("buttonmousedown", _, $)
	}
});
mini.copyTo(Gi2Z.prototype, {
	N91: function(_, A) {
		var C = "<div class=\"mini-tools\">";
		if(A) C += "<span class=\"mini-tools-collapse\"></span>";
		else for(var $ = _.buttons.length - 1; $ >= 0; $--) {
			var B = _.buttons[$];
			C += "<span class=\"" + B.cls + "\" style=\"";
			C += B.style + ";" + (B.visible ? "" : "display:none;") + "\">" + B.html + "</span>"
		}
		C += "</div>";
		C += "<div class=\"mini-layout-region-icon " + _.iconCls + "\" style=\"" + _[DP] + ";" + ((_[DP] || _.iconCls) ? "" : "display:none;") + "\"></div>";
		C += "<div class=\"mini-layout-region-title\">" + _.title + "</div>";
		return C
	},
	doUpdate: function() {
		for(var $ = 0, E = this.regions.length; $ < E; $++) {
			var B = this.regions[$],
				_ = B.region,
				A = B._el,
				D = B._split,
				C = B._proxy;
			B._header.style.display = B.showHeader ? "" : "none";
			B._header.innerHTML = this.N91(B);
			if(B._proxy) B._proxy.innerHTML = this.N91(B, true);
			if(D) {
				HMT(D, "mini-layout-split-nodrag");
				if(B.expanded == false || !B[JkX]) V7(D, "mini-layout-split-nodrag")
			}
		}
		this[Fcv]()
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		if(this.Ms3) return;
		var C = KwY(this.el, true),
			_ = R0(this.el, true),
			D = {
				x: 0,
				y: 0,
				width: _,
				height: C
			},
			I = this.regions.clone(),
			P = this[JN]("center");
		I.remove(P);
		if(P) I.push(P);
		for(var K = 0, H = I.length; K < H; K++) {
			var E = I[K];
			E._Expanded = false;
			HMT(E._el, "mini-layout-popup");
			var A = E.region,
				L = E._el,
				F = E._split,
				G = E._proxy;
			if(E.visible == false) {
				L.style.display = "none";
				if(A != "center") F.style.display = G.style.display = "none";
				continue
			}
			L.style.display = "";
			if(A != "center") F.style.display = G.style.display = "";
			var R = D.x,
				O = D.y,
				_ = D.width,
				C = D.height,
				B = E.width,
				J = E.height;
			if(!E.expanded) if(A == "west" || A == "east") {
				B = E.collapseSize;
				Sbkj(L, E.width)
			} else if(A == "north" || A == "south") {
				J = E.collapseSize;
				SmU(L, E.height)
			}
			switch(A) {
			case "north":
				C = J;
				D.y += J;
				D.height -= J;
				break;
			case "south":
				C = J;
				O = D.y + D.height - J;
				D.height -= J;
				break;
			case "west":
				_ = B;
				D.x += B;
				D.width -= B;
				break;
			case "east":
				_ = B;
				R = D.x + D.width - B;
				D.width -= B;
				break;
			case "center":
				break;
			default:
				continue
			}
			if(_ < 0) _ = 0;
			if(C < 0) C = 0;
			if(A == "west" || A == "east") SmU(L, C);
			if(A == "north" || A == "south") Sbkj(L, _);
			var N = "left:" + R + "px;top:" + O + "px;",
				$ = L;
			if(!E.expanded) {
				$ = G;
				L.style.top = "-100px";
				L.style.left = "-1500px"
			} else if(G) {
				G.style.left = "-1500px";
				G.style.top = "-100px"
			}
			$.style.left = R + "px";
			$.style.top = O + "px";
			Sbkj($, _);
			SmU($, C);
			var M = jQuery(E._el).height(),
				Q = E.showHeader ? jQuery(E._header).outerHeight() : 0;
			SmU(E._body, M - Q);
			if(A == "center") continue;
			B = J = E.splitSize;
			R = D.x, O = D.y, _ = D.width, C = D.height;
			switch(A) {
			case "north":
				C = J;
				D.y += J;
				D.height -= J;
				break;
			case "south":
				C = J;
				O = D.y + D.height - J;
				D.height -= J;
				break;
			case "west":
				_ = B;
				D.x += B;
				D.width -= B;
				break;
			case "east":
				_ = B;
				R = D.x + D.width - B;
				D.width -= B;
				break;
			case "center":
				break
			}
			if(_ < 0) _ = 0;
			if(C < 0) C = 0;
			F.style.left = R + "px";
			F.style.top = O + "px";
			Sbkj(F, _);
			SmU(F, C);
			F.style.display = E.showSplit ? "block" : "none"
		}
		mini.layout(this.TG)
	},
	AOlf: function(B) {
		if(this.Ms3) return;
		if(ZW(B.target, "mini-layout-split")) {
			var A = jQuery(B.target).attr("uid");
			if(A != this.uid) return;
			var _ = this[JN](B.target.id);
			if(_.expanded == false || !_[JkX]) return;
			this.dragRegion = _;
			var $ = this.IN4();
			$.start(B)
		}
	},
	IN4: function() {
		if(!this.drag) this.drag = new mini.Drag({
			capture: true,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this.drag
	},
	Jb: function($) {
		this.$UZs = mini.append(document.body, "<div class=\"mini-resizer-mask\"></div>");
		this.JUge = mini.append(document.body, "<div class=\"mini-proxy\"></div>");
		this.JUge.style.cursor = "n-resize";
		if(this.dragRegion.region == "west" || this.dragRegion.region == "east") this.JUge.style.cursor = "w-resize";
		this.splitBox = Y3L(this.dragRegion._split);
		$vA(this.JUge, this.splitBox);
		this.elBox = Y3L(this.el, true)
	},
	Pc4: function(C) {
		var I = C.now[0] - C.init[0],
			V = this.splitBox.x + I,
			A = C.now[1] - C.init[1],
			U = this.splitBox.y + A,
			K = V + this.splitBox.width,
			T = U + this.splitBox.height,
			G = this[JN]("west"),
			L = this[JN]("east"),
			F = this[JN]("north"),
			D = this[JN]("south"),
			H = this[JN]("center"),
			O = G && G.visible ? G.width : 0,
			Q = L && L.visible ? L.width : 0,
			R = F && F.visible ? F.height : 0,
			J = D && D.visible ? D.height : 0,
			P = G && G.showSplit ? R0(G._split) : 0,
			$ = L && L.showSplit ? R0(L._split) : 0,
			B = F && F.showSplit ? KwY(F._split) : 0,
			S = D && D.showSplit ? KwY(D._split) : 0,
			E = this.dragRegion,
			N = E.region;
		if(N == "west") {
			var M = this.elBox.width - Q - $ - P - H.minWidth;
			if(V - this.elBox.x > M) V = M + this.elBox.x;
			if(V - this.elBox.x < E.minWidth) V = E.minWidth + this.elBox.x;
			if(V - this.elBox.x > E.maxWidth) V = E.maxWidth + this.elBox.x;
			mini.setX(this.JUge, V)
		} else if(N == "east") {
			M = this.elBox.width - O - P - $ - H.minWidth;
			if(this.elBox.right - (V + this.splitBox.width) > M) V = this.elBox.right - M - this.splitBox.width;
			if(this.elBox.right - (V + this.splitBox.width) < E.minWidth) V = this.elBox.right - E.minWidth - this.splitBox.width;
			if(this.elBox.right - (V + this.splitBox.width) > E.maxWidth) V = this.elBox.right - E.maxWidth - this.splitBox.width;
			mini.setX(this.JUge, V)
		} else if(N == "north") {
			var _ = this.elBox.height - J - S - B - H.minHeight;
			if(U - this.elBox.y > _) U = _ + this.elBox.y;
			if(U - this.elBox.y < E.minHeight) U = E.minHeight + this.elBox.y;
			if(U - this.elBox.y > E.maxHeight) U = E.maxHeight + this.elBox.y;
			mini.setY(this.JUge, U)
		} else if(N == "south") {
			_ = this.elBox.height - R - B - S - H.minHeight;
			if(this.elBox.bottom - (U + this.splitBox.height) > _) U = this.elBox.bottom - _ - this.splitBox.height;
			if(this.elBox.bottom - (U + this.splitBox.height) < E.minHeight) U = this.elBox.bottom - E.minHeight - this.splitBox.height;
			if(this.elBox.bottom - (U + this.splitBox.height) > E.maxHeight) U = this.elBox.bottom - E.maxHeight - this.splitBox.height;
			mini.setY(this.JUge, U)
		}
	},
	IzO: function(B) {
		var C = Y3L(this.JUge),
			D = this.dragRegion,
			A = D.region;
		if(A == "west") {
			var $ = C.x - this.elBox.x;
			this.updateRegion(D, {
				width: $
			})
		} else if(A == "east") {
			$ = this.elBox.right - C.right;
			this.updateRegion(D, {
				width: $
			})
		} else if(A == "north") {
			var _ = C.y - this.elBox.y;
			this.updateRegion(D, {
				height: _
			})
		} else if(A == "south") {
			_ = this.elBox.bottom - C.bottom;
			this.updateRegion(D, {
				height: _
			})
		}
		jQuery(this.JUge).remove();
		this.JUge = null;
		this.elBox = this.handlerBox = null;
		jQuery(this.$UZs).remove();
		this.$UZs = null
	},
	Fesk: function($) {
		$ = this[JN]($);
		if($._Expanded === true) this.A$Bk($);
		else this.Vws($)
	},
	Vws: function(D) {
		if(this.Ms3) return;
		this[Fcv]();
		var A = D.region,
			H = D._el;
		D._Expanded = true;
		V7(H, "mini-layout-popup");
		var E = Y3L(D._proxy),
			B = Y3L(D._el),
			F = {};
		if(A == "east") {
			var K = E.x,
				J = E.y,
				C = E.height;
			SmU(H, C);
			mini[Wib](H, K, J);
			var I = parseInt(H.style.left);
			F = {
				left: I - B.width
			}
		} else if(A == "west") {
			K = E.right - B.width, J = E.y, C = E.height;
			SmU(H, C);
			mini[Wib](H, K, J);
			I = parseInt(H.style.left);
			F = {
				left: I + B.width
			}
		} else if(A == "north") {
			var K = E.x,
				J = E.bottom - B.height,
				_ = E.width;
			Sbkj(H, _);
			mini[Wib](H, K, J);
			var $ = parseInt(H.style.top);
			F = {
				top: $ + B.height
			}
		} else if(A == "south") {
			K = E.x, J = E.y, _ = E.width;
			Sbkj(H, _);
			mini[Wib](H, K, J);
			$ = parseInt(H.style.top);
			F = {
				top: $ - B.height
			}
		}
		V7(D._proxy, "mini-layout-maxZIndex");
		this.Ms3 = true;
		var G = this,
			L = jQuery(H);
		L.animate(F, 250, function() {
			HMT(D._proxy, "mini-layout-maxZIndex");
			G.Ms3 = false
		})
	},
	A$Bk: function(F) {
		if(this.Ms3) return;
		F._Expanded = false;
		var B = F.region,
			E = F._el,
			D = Y3L(E),
			_ = {};
		if(B == "east") {
			var C = parseInt(E.style.left);
			_ = {
				left: C + D.width
			}
		} else if(B == "west") {
			C = parseInt(E.style.left);
			_ = {
				left: C - D.width
			}
		} else if(B == "north") {
			var $ = parseInt(E.style.top);
			_ = {
				top: $ - D.height
			}
		} else if(B == "south") {
			$ = parseInt(E.style.top);
			_ = {
				top: $ + D.height
			}
		}
		V7(F._proxy, "mini-layout-maxZIndex");
		this.Ms3 = true;
		var A = this,
			G = jQuery(E);
		G.animate(_, 250, function() {
			HMT(F._proxy, "mini-layout-maxZIndex");
			A.Ms3 = false;
			A[Fcv]()
		})
	},
	UgY: function(B) {
		if(this.Ms3) return;
		for(var $ = 0, A = this.regions.length; $ < A; $++) {
			var _ = this.regions[$];
			if(!_._Expanded) continue;
			if(TWAc(_._el, B.target) || TWAc(_._proxy, B.target));
			else this.A$Bk(_)
		}
	},
	getAttrs: function(A) {
		var H = Gi2Z[GR_][$gN][GkN](this, A),
			G = jQuery(A),
			E = parseInt(G.attr("splitSize"));
		if(!isNaN(E)) H.splitSize = E;
		var F = [],
			D = mini[OIAh](A);
		for(var _ = 0, C = D.length; _ < C; _++) {
			var B = D[_],
				$ = {};
			F.push($);
			$.cls = B.className;
			$.style = B.style.cssText;
			mini[ENl](B, $, ["region", "title", "iconCls", "iconStyle", "cls", "headerCls", "headerStyle", "bodyCls", "bodyStyle"]);
			mini[XD9s](B, $, ["allowResize", "visible", "showCloseButton", "showCollapseButton", "showSplit", "showHeader", "expanded"]);
			mini[GGt](B, $, ["splitSize", "collapseSize", "width", "height", "minWidth", "minHeight", "maxWidth", "maxHeight"]);
			$.bodyParent = B
		}
		H.regions = F;
		return H
	}
});
PC7(Gi2Z, "layout");
W4 = function() {
	W4[GR_][KNT][GkN](this)
};
Iov(W4, FIa, {
	style: "",
	borderStyle: "",
	bodyStyle: "",
	uiCls: "mini-box",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-box";
		this.el.innerHTML = "<div class=\"mini-box-border\"></div>";
		this.H23I = this.TG = this.el.firstChild
	},
	_initEvents: function() {},
	doLayout: function() {
		if(!this.canLayout()) return;
		var C = this[_H](),
			E = this[J4j](),
			B = VO(this.H23I),
			D = TA$(this.H23I);
		if(!C) {
			var A = this[DTTy](true);
			if(jQuery.boxModel) A = A - B.top - B.bottom;
			A = A - D.top - D.bottom;
			if(A < 0) A = 0;
			this.H23I.style.height = A + "px"
		} else this.H23I.style.height = "";
		var $ = this[Y1Q](true),
			_ = $;
		$ = $ - D.left - D.right;
		if(jQuery.boxModel) $ = $ - B.left - B.right;
		if($ < 0) $ = 0;
		this.H23I.style.width = $ + "px";
		mini.layout(this.TG)
	},
	setBody: function(_) {
		if(!_) return;
		if(!mini.isArray(_)) _ = [_];
		for(var $ = 0, A = _.length; $ < A; $++) mini.append(this.H23I, _[$]);
		mini.parse(this.H23I);
		this[Fcv]()
	},
	set_bodyParent: function($) {
		if(!$) return;
		var _ = this.H23I,
			A = $;
		while(A.firstChild) _.appendChild(A.firstChild);
		this[Fcv]()
	},
	setBodyStyle: function($) {
		Gn(this.H23I, $);
		this[Fcv]()
	},
	getAttrs: function($) {
		var _ = W4[GR_][$gN][GkN](this, $);
		_._bodyParent = $;
		mini[ENl]($, _, ["bodyStyle"]);
		return _
	}
});
PC7(W4, "box");
DeC = function() {
	DeC[GR_][KNT][GkN](this)
};
Iov(DeC, FIa, {
	url: "",
	uiCls: "mini-include",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-include"
	},
	_initEvents: function() {},
	doLayout: function() {
		if(!this.canLayout()) return;
		var A = this.el.childNodes;
		if(A) for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$];
			mini.layout(_)
		}
	},
	setUrl: function($) {
		this.url = $;
		mini.update({
			url: this.url,
			el: this.el,
			async: this.async
		});
		this[Fcv]()
	},
	getUrl: function($) {
		return this.url
	},
	getAttrs: function($) {
		var _ = DeC[GR_][$gN][GkN](this, $);
		mini[ENl]($, _, ["url"]);
		return _
	}
});
PC7(DeC, "include");
Ck3 = function() {
	this.JAT();
	Ck3[GR_][KNT][GkN](this)
};
Iov(Ck3, FIa, {
	activeIndex: -1,
	tabAlign: "left",
	tabPosition: "top",
	showBody: true,
	nameField: "id",
	titleField: "title",
	urlField: "url",
	url: "",
	maskOnLoad: true,
	bodyStyle: "",
	TAuX: "mini-tab-hover",
	TA8: "mini-tab-active",
	set: function(_) {
		if(typeof _ == "string") return this;
		var $ = this.ERW;
		this.ERW = false;
		var A = _.activeIndex;
		delete _.activeIndex;
		var B = _.url;
		delete _.url;
		Ck3[GR_].set[GkN](this, _);
		if(B) this.setUrl(B);
		if(mini.isNumber(A)) this.setActiveIndex(A);
		this.ERW = $;
		this[Fcv]();
		return this
	},
	uiCls: "mini-tabs",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-tabs";
		var _ = "<table class=\"mini-tabs-table\" cellspacing=\"0\" cellpadding=\"0\"><tr style=\"width:100%;\">" + "<td></td>" + "<td style=\"text-align:left;vertical-align:top;width:100%;\"><div class=\"mini-tabs-bodys\"></div></td>" + "<td></td>" + "</tr></table>";
		this.el.innerHTML = _;
		this.It = this.el.firstChild;
		var $ = this.el.getElementsByTagName("td");
		this.NA = $[0];
		this.E3 = $[1];
		this.Y81M = $[2];
		this.H23I = this.E3.firstChild;
		this.TG = this.H23I;
		this[Mdk]()
	},
	Y$Y: function() {
		HMT(this.NA, "mini-tabs-header");
		HMT(this.Y81M, "mini-tabs-header");
		this.NA.innerHTML = "";
		this.Y81M.innerHTML = "";
		mini.removeChilds(this.E3, this.H23I)
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "mousedown", this.AOlf, this);
			$v9(this.el, "click", this.PGY, this);
			$v9(this.el, "mouseover", this.LZR, this);
			$v9(this.el, "mouseout", this.$MHa, this)
		}, this)
	},
	JAT: function() {
		this.tabs = []
	},
	QaT: 1,
	createTab: function(_) {
		var $ = mini.copyTo({
			_id: this.QaT++,
			name: "",
			title: "",
			newLine: false,
			iconCls: "",
			iconStyle: "",
			headerCls: "",
			headerStyle: "",
			bodyCls: "",
			bodyStyle: "",
			visible: true,
			enabled: true,
			showCloseButton: false,
			active: false,
			url: "",
			loaded: false,
			refreshOnClick: false
		}, _);
		if(_) {
			_ = mini.copyTo(_, $);
			$ = _
		}
		return $
	},
	YqFk: function() {
		var _ = mini.getData(this.url);
		if(!_) _ = [];
		for(var $ = 0, B = _.length; $ < B; $++) {
			var A = _[$];
			A.title = A[this.titleField];
			A.url = A[this.urlField];
			A.name = A[this.nameField]
		}
		this.setTabs(_);
		this.fire("load")
	},
	load: function($) {
		if(typeof $ == "string") this.setUrl($);
		else this.setTabs($)
	},
	setUrl: function($) {
		this.url = $;
		this.YqFk()
	},
	getUrl: function() {
		return this.url
	},
	setNameField: function($) {
		this.nameField = $
	},
	getNameField: function() {
		return this.nameField
	},
	setTitleField: function($) {
		this[D7b] = $
	},
	getTitleField: function() {
		return this[D7b]
	},
	setUrlField: function($) {
		this[PP] = $
	},
	getUrlField: function() {
		return this[PP]
	},
	setTabs: function(_) {
		if(!mini.isArray(_)) return;
		this.beginUpdate();
		this.removeAll();
		for(var $ = 0, A = _.length; $ < A; $++) this.addTab(_[$]);
		this.setActiveIndex(0);
		this.endUpdate()
	},
	getTabs: function() {
		return this.tabs
	},
	removeAll: function(A) {
		if(mini.isNull(A)) A = [];
		if(!mini.isArray(A)) A = [A];
		for(var $ = A.length - 1; $ >= 0; $--) {
			var B = this.getTab(A[$]);
			if(!B) A.removeAt($);
			else A[$] = B
		}
		var _ = this.tabs;
		for($ = _.length - 1; $ >= 0; $--) {
			var D = _[$];
			if(A.indexOf(D) == -1) this.removeTab(D)
		}
		var C = A[0];
		if(C) this.activeTab(C)
	},
	addTab: function(C, $) {
		if(typeof C == "string") C = {
			title: C
		};
		C = this.createTab(C);
		if(!C.name) C.name = "";
		if(typeof $ != "number") $ = this.tabs.length;
		this.tabs.insert($, C);
		var F = this.$BHi(C),
			G = "<div id=\"" + F + "\" class=\"mini-tabs-body " + C.bodyCls + "\" style=\"" + C.bodyStyle + ";display:none;\"></div>";
		mini.append(this.H23I, G);
		var A = this.getTabBodyEl(C),
			B = C.body;
		delete C.body;
		if(B) {
			if(!mini.isArray(B)) B = [B];
			for(var _ = 0, E = B.length; _ < E; _++) mini.append(A, B[_])
		}
		if(C.bodyParent) {
			var D = C.bodyParent;
			while(D.firstChild) A.appendChild(D.firstChild)
		}
		delete C.bodyParent;
		this[Mdk]();
		return C
	},
	removeTab: function(C) {
		C = this.getTab(C);
		if(!C) return;
		var D = this.getActiveTab(),
			B = C == D,
			A = this.StJt(C);
		this.tabs.remove(C);
		this.SUcw(C);
		var _ = this.getTabBodyEl(C);
		if(_) this.H23I.removeChild(_);
		if(A && B) {
			for(var $ = this.activeIndex; $ >= 0; $--) {
				var C = this.getTab($);
				if(C && C.enabled && C.visible) {
					this.activeIndex = $;
					break
				}
			}
			this[Mdk]();
			this.setActiveIndex(this.activeIndex);
			this.fire("activechanged")
		} else {
			this.activeIndex = this.tabs.indexOf(D);
			this[Mdk]()
		}
		return C
	},
	moveTab: function(A, $) {
		A = this.getTab(A);
		if(!A) return;
		var _ = this.tabs[$];
		if(!_ || _ == A) return;
		this.tabs.remove(A);
		var $ = this.tabs.indexOf(_);
		this.tabs.insert($, A);
		this[Mdk]()
	},
	updateTab: function($, _) {
		$ = this.getTab($);
		if(!$) return;
		mini.copyTo($, _);
		this[Mdk]()
	},
	Qcra: function() {
		return this.H23I
	},
	SUcw: function(B) {
		if(B.BLTH && B.BLTH.parentNode) {
			B.BLTH._ondestroy();
			B.BLTH.parentNode.removeChild(B.BLTH);
			B.BLTH = null
		}
		var C = this.getTabBodyEl(B);
		if(C) {
			var A = mini[OIAh](C, true);
			for(var _ = 0, D = A.length; _ < D; _++) {
				var $ = A[_];
				if($ && $.parentNode) $.parentNode.removeChild($)
			}
		}
	},
	Izz: 180,
	KmG: function(A) {
		if(!A) return;
		var B = this.getTabBodyEl(A);
		if(!B) return;
		this._loading = true;
		this.SUcw(A);
		this.isLoading = true;
		if(this.maskOnLoad) this.loading();
		var C = new Date(),
			$ = this,
			_ = mini.createIFrame(A.url, function(_, D) {
				var B = (C - new Date()) + $.Izz;
				if(B < 0) B = 0;
				setTimeout(function() {
					$.unmask();
					$[Fcv]();
					$.isLoading = false
				}, B);
				try {
					A.BLTH.contentWindow.Owner = window;
					A.BLTH.contentWindow.CloseOwnerWindow = function(_) {
						setTimeout(function() {
							A.removeAction = _;
							$.removeTab(A)
						}, 1)
					}
				} catch(E) {}
				var E = {
					sender: $,
					tab: A,
					index: $.tabs.indexOf(A),
					name: A.name,
					iframe: A.BLTH
				};
				if(A.onload) {
					if(typeof A.onload == "string") A.onload = window[A.onload];
					if(A.onload) A.onload[GkN]($, E)
				}
				$.fire("tabload", E)
			}, function() {
				if($.el == null) return;
				$.isLoading = false
			});
		setTimeout(function() {
			if(A.BLTH == _) B.appendChild(_)
		}, 1);
		A.BLTH = _;
		A.loadedUrl = A.url
	},
	StJt: function($) {
		var _ = {
			sender: this,
			tab: $,
			index: this.tabs.indexOf($),
			name: $.name,
			iframe: $.BLTH,
			autoActive: true
		};
		if($.ondestroy) {
			if(typeof $.ondestroy == "string") $.ondestroy = window[$.ondestroy];
			if($.ondestroy) {
				try {
					$.ondestroy[GkN](this, _)
				} catch(_) {}
			}
		}
		this.fire("tabdestroy", _);
		return _.autoActive
	},
	loadTab: function(A, _, $, B) {
		if(!A) return;
		_ = this.getTab(_);
		if(!_) _ = this.getActiveTab();
		if(!_) return;
		_.url = A;
		this.KmG(_)
	},
	reloadTab: function($) {
		$ = this.getTab($);
		if(!$) $ = this.getActiveTab();
		if(!$) return;
		this.loadTab($.url, $)
	},
	getTabRows: function() {
		var A = [],
			_ = [];
		for(var $ = 0, C = this.tabs.length; $ < C; $++) {
			var B = this.tabs[$];
			if($ != 0 && B.newLine) {
				A.push(_);
				_ = []
			}
			_.push(B)
		}
		A.push(_);
		return A
	},
	doUpdate: function() {
		if(this.Y5o === false) return;
		HMT(this.el, "mini-tabs-position-left");
		HMT(this.el, "mini-tabs-position-top");
		HMT(this.el, "mini-tabs-position-right");
		HMT(this.el, "mini-tabs-position-bottom");
		if(this[_8K6] == "bottom") {
			V7(this.el, "mini-tabs-position-bottom");
			this.Erk()
		} else if(this[_8K6] == "right") {
			V7(this.el, "mini-tabs-position-right");
			this.DDf()
		} else if(this[_8K6] == "left") {
			V7(this.el, "mini-tabs-position-left");
			this.JFL()
		} else {
			V7(this.el, "mini-tabs-position-top");
			this.Uin()
		}
		this[Fcv]();
		this.setActiveIndex(this.activeIndex, false)
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		var R = this[_H]();
		C = this[DTTy](true);
		w = this[Y1Q](true);
		var G = C,
			O = w;
		if(!R && this[Q$]) {
			var Q = jQuery(this.NL).outerHeight(),
				$ = jQuery(this.NL).outerWidth();
			if(this[_8K6] == "top") Q = jQuery(this.NL.parentNode).outerHeight();
			if(this[_8K6] == "left" || this[_8K6] == "right") w = w - $;
			else C = C - Q;
			if(jQuery.boxModel) {
				var D = VO(this.H23I),
					S = D0uu(this.H23I);
				C = C - D.top - D.bottom - S.top - S.bottom;
				w = w - D.left - D.right - S.left - S.right
			}
			margin = TA$(this.H23I);
			C = C - margin.top - margin.bottom;
			w = w - margin.left - margin.right;
			if(C < 0) C = 0;
			if(w < 0) w = 0;
			this.H23I.style.width = w + "px";
			this.H23I.style.height = C + "px";
			if(this[_8K6] == "left" || this[_8K6] == "right") {
				var I = this.NL.getElementsByTagName("tr")[0],
					E = I.childNodes,
					_ = E[0].getElementsByTagName("tr"),
					F = last = all = 0;
				for(var K = 0, H = _.length; K < H; K++) {
					var I = _[K],
						N = jQuery(I).outerHeight();
					all += N;
					if(K == 0) F = N;
					if(K == H - 1) last = N
				}
				switch(this[V6d]) {
				case "center":
					var P = parseInt((G - (all - F - last)) / 2);
					for(K = 0, H = E.length; K < H; K++) {
						E[K].firstChild.style.height = G + "px";
						var B = E[K].firstChild,
							_ = B.getElementsByTagName("tr"),
							L = _[0],
							U = _[_.length - 1];
						L.style.height = P + "px";
						U.style.height = P + "px"
					}
					break;
				case "right":
					for(K = 0, H = E.length; K < H; K++) {
						var B = E[K].firstChild,
							_ = B.getElementsByTagName("tr"),
							I = _[0],
							T = G - (all - F);
						if(T >= 0) I.style.height = T + "px"
					}
					break;
				case "fit":
					for(K = 0, H = E.length; K < H; K++) E[K].firstChild.style.height = G + "px";
					break;
				default:
					for(K = 0, H = E.length; K < H; K++) {
						B = E[K].firstChild, _ = B.getElementsByTagName("tr"), I = _[_.length - 1], T = G - (all - last);
						if(T >= 0) I.style.height = T + "px"
					}
					break
				}
			}
		} else {
			this.H23I.style.width = "auto";
			this.H23I.style.height = "auto"
		}
		var A = this.getTabBodyEl(this.activeIndex);
		if(A) if(!R && this[Q$]) {
			var C = KwY(this.H23I, true);
			if(jQuery.boxModel) {
				D = VO(A), S = D0uu(A);
				C = C - D.top - D.bottom - S.top - S.bottom
			}
			A.style.height = C + "px"
		} else A.style.height = "auto";
		switch(this[_8K6]) {
		case "bottom":
			var M = this.NL.childNodes;
			for(K = 0, H = M.length; K < H; K++) {
				B = M[K];
				HMT(B, "mini-tabs-header2");
				if(H > 1 && K != 0) V7(B, "mini-tabs-header2")
			}
			break;
		case "left":
			E = this.NL.firstChild.rows[0].cells;
			for(K = 0, H = E.length; K < H; K++) {
				var J = E[K];
				HMT(J, "mini-tabs-header2");
				if(H > 1 && K == 0) V7(J, "mini-tabs-header2")
			}
			break;
		case "right":
			E = this.NL.firstChild.rows[0].cells;
			for(K = 0, H = E.length; K < H; K++) {
				J = E[K];
				HMT(J, "mini-tabs-header2");
				if(H > 1 && K != 0) V7(J, "mini-tabs-header2")
			}
			break;
		default:
			M = this.NL.childNodes;
			for(K = 0, H = M.length; K < H; K++) {
				B = M[K];
				HMT(B, "mini-tabs-header2");
				if(H > 1 && K == 0) V7(B, "mini-tabs-header2")
			}
			break
		}
		HMT(this.el, "mini-tabs-scroll");
		if(this[_8K6] == "top") {
			jQuery(this.NL).width(O);
			if(this.NL.offsetWidth < this.NL.scrollWidth) {
				jQuery(this.NL).width(O - 60);
				V7(this.el, "mini-tabs-scroll")
			}
			if(isIE && !jQuery.boxModel) this.WZ6s.style.left = "-26px"
		}
		this.OJ();
		mini.layout(this.H23I)
	},
	setTabAlign: function($) {
		this[V6d] = $;
		this[Mdk]()
	},
	setTabPosition: function($) {
		this[_8K6] = $;
		this[Mdk]()
	},
	getTab: function($) {
		if(typeof $ == "object") return $;
		if(typeof $ == "number") return this.tabs[$];
		else for(var _ = 0, B = this.tabs.length; _ < B; _++) {
			var A = this.tabs[_];
			if(A.name == $) return A
		}
	},
	getHeaderEl: function() {
		return this.NL
	},
	getBodyEl: function() {
		return this.H23I
	},
	getTabEl: function($) {
		var C = this.getTab($);
		if(!C) return null;
		var E = this.T6ao(C),
			B = this.el.getElementsByTagName("*");
		for(var _ = 0, D = B.length; _ < D; _++) {
			var A = B[_];
			if(A.id == E) return A
		}
		return null
	},
	getTabBodyEl: function($) {
		var C = this.getTab($);
		if(!C) return null;
		var E = this.$BHi(C),
			B = this.H23I.childNodes;
		for(var _ = 0, D = B.length; _ < D; _++) {
			var A = B[_];
			if(A.id == E) return A
		}
		return null
	},
	getTabIFrameEl: function($) {
		var _ = this.getTab($);
		if(!_) return null;
		return _.BLTH
	},
	T6ao: function($) {
		return this.uid + "$" + $._id
	},
	$BHi: function($) {
		return this.uid + "$body$" + $._id
	},
	OJ: function() {
		if(this[_8K6] == "top") {
			HMT(this.WZ6s, "mini-disabled");
			HMT(this.RAG, "mini-disabled");
			if(this.NL.scrollLeft == 0) V7(this.WZ6s, "mini-disabled");
			var _ = this.getTabEl(this.tabs.length - 1);
			if(_) {
				var $ = Y3L(_),
					A = Y3L(this.NL);
				if($.right <= A.right) V7(this.RAG, "mini-disabled")
			}
		}
	},
	setActiveIndex: function($, I) {
		var M = this.getTab($),
			C = this.getTab(this.activeIndex),
			N = M != C,
			K = this.getTabBodyEl(this.activeIndex);
		if(K) K.style.display = "none";
		if(M) this.activeIndex = this.tabs.indexOf(M);
		else this.activeIndex = -1;
		K = this.getTabBodyEl(this.activeIndex);
		if(K) K.style.display = "";
		K = this.getTabEl(C);
		if(K) HMT(K, this.TA8);
		K = this.getTabEl(M);
		if(K) V7(K, this.TA8);
		if(K && N) {
			if(this[_8K6] == "bottom") {
				var A = ZW(K, "mini-tabs-header");
				if(A) jQuery(this.NL).prepend(A)
			} else if(this[_8K6] == "left") {
				var G = ZW(K, "mini-tabs-header").parentNode;
				if(G) G.parentNode.appendChild(G)
			} else if(this[_8K6] == "right") {
				G = ZW(K, "mini-tabs-header").parentNode;
				if(G) jQuery(G.parentNode).prepend(G)
			} else {
				A = ZW(K, "mini-tabs-header");
				if(A) this.NL.appendChild(A)
			}
			var B = this.NL.scrollLeft;
			this[Fcv]();
			var _ = this.getTabRows();
			if(_.length > 1);
			else {
				if(this[_8K6] == "top") {
					this.NL.scrollLeft = B;
					var O = this.getTabEl(this.activeIndex);
					if(O) {
						var J = this,
							L = Y3L(O),
							F = Y3L(J.NL);
						if(L.x < F.x) J.NL.scrollLeft -= (F.x - L.x);
						else if(L.right > F.right) J.NL.scrollLeft += (L.right - F.right)
					}
				}
				this.OJ()
			}
			for(var H = 0, E = this.tabs.length; H < E; H++) {
				O = this.getTabEl(this.tabs[H]);
				if(O) HMT(O, this.TAuX)
			}
		}
		var D = this;
		if(N) {
			var P = {
				tab: M,
				index: this.tabs.indexOf(M),
				name: M.name
			};
			setTimeout(function() {
				D.fire("ActiveChanged", P)
			}, 1)
		}
		if(I !== false) if(M && M.url && !M.loadedUrl) D.loadTab(M.url, M);
		if(D.canLayout()) {
			try {
				mini.layoutIFrames(D.el)
			} catch(P) {}
		}
	},
	getActiveIndex: function() {
		return this.activeIndex
	},
	activeTab: function($) {
		this.setActiveIndex($)
	},
	getActiveTab: function() {
		return this.getTab(this.activeIndex)
	},
	getActiveIndex: function() {
		return this.activeIndex
	},
	JW: function(_) {
		_ = this.getTab(_);
		if(!_) return;
		var $ = this.tabs.indexOf(_);
		if(this.activeIndex == $) return;
		var A = {
			tab: _,
			index: $,
			name: _.name,
			cancel: false
		};
		this.fire("BeforeActiveChanged", A);
		if(A.cancel == false) this.activeTab(_)
	},
	setShowBody: function($) {
		if(this[Q$] != $) {
			this[Q$] = $;
			this[Fcv]()
		}
	},
	getShowBody: function() {
		return this[Q$]
	},
	setBodyStyle: function($) {
		this.bodyStyle = $;
		Gn(this.H23I, $);
		this[Fcv]()
	},
	getBodyStyle: function() {
		return this.bodyStyle
	},
	setMaskOnLoad: function($) {
		this.maskOnLoad = $
	},
	getMaskOnLoad: function() {
		return this.maskOnLoad
	},
	getTabByEvent: function($) {
		return this.O7r6($)
	},
	O7r6: function(B) {
		var A = ZW(B.target, "mini-tab");
		if(!A) return null;
		var _ = A.id.split("$");
		if(_[0] != this.uid) return null;
		var $ = parseInt(jQuery(A).attr("index"));
		return this.getTab($)
	},
	PGY: function(A) {
		if(this.isLoading) return;
		var _ = this.O7r6(A);
		if(!_) return;
		if(_.enabled) if(ZW(A.target, "mini-tab-close")) this.Ita(_, A);
		else {
			var $ = _.loadedUrl;
			this.JW(_);
			if(_[EPnR] && _.url == $) this.reloadTab(_)
		}
	},
	hoverTab: null,
	LZR: function(A) {
		var $ = this.O7r6(A);
		if($ && $.enabled) {
			var _ = this.getTabEl($);
			V7(_, this.TAuX);
			this.hoverTab = $
		}
	},
	$MHa: function(_) {
		if(this.hoverTab) {
			var $ = this.getTabEl(this.hoverTab);
			HMT($, this.TAuX)
		}
		this.hoverTab = null
	},
	AOlf: function(B) {
		if(this[_8K6] == "top") {
			var _ = this,
				A = 0,
				$ = 10;
			if(B.target == this.WZ6s) this.FTzX = setInterval(function() {
				_.NL.scrollLeft -= $;
				A++;
				if(A > 5) $ = 18;
				if(A > 10) $ = 25;
				_.OJ()
			}, 20);
			else if(B.target == this.RAG) this.FTzX = setInterval(function() {
				_.NL.scrollLeft += $;
				A++;
				if(A > 5) $ = 18;
				if(A > 10) $ = 25;
				_.OJ()
			}, 20);
			$v9(document, "mouseup", this.V4ht, this)
		}
	},
	V4ht: function($) {
		clearInterval(this.FTzX);
		this.FTzX = null;
		M4(document, "mouseup", this.V4ht, this)
	},
	Uin: function() {
		var L = this[_8K6] == "top",
			O = "";
		if(L) {
			O += "<div class=\"mini-tabs-scrollCt\">";
			O += "<a class=\"mini-tabs-leftButton\" href=\"javascript:void(0)\" hideFocus onclick=\"return false\"></a><a class=\"mini-tabs-rightButton\" href=\"javascript:void(0)\" hideFocus onclick=\"return false\"></a>"
		}
		O += "<div class=\"mini-tabs-headers\">";
		var B = this.getTabRows();
		for(var M = 0, A = B.length; M < A; M++) {
			var I = B[M],
				E = "";
			O += "<table class=\"mini-tabs-header\" cellspacing=\"0\" cellpadding=\"0\"><tr><td class=\"mini-tabs-space mini-tabs-firstSpace\"><div></div></td>";
			for(var J = 0, F = I.length; J < F; J++) {
				var N = I[J],
					G = this.T6ao(N);
				if(!N.visible) continue;
				var $ = this.tabs.indexOf(N),
					E = N.headerCls || "";
				if(N.enabled == false) E += " mini-disabled";
				O += "<td id=\"" + G + "\" index=\"" + $ + "\"  class=\"mini-tab " + E + "\" style=\"" + N.headerStyle + "\">";
				if(N.iconCls || N[DP]) O += "<span class=\"mini-tab-icon " + N.iconCls + "\" style=\"" + N[DP] + "\"></span>";
				O += "<span class=\"mini-tab-text\">" + N.title + "</span>";
				if(N[$KS]) {
					var _ = "";
					if(N.enabled) _ = "onmouseover=\"V7(this,'mini-tab-close-hover')\" onmouseout=\"HMT(this,'mini-tab-close-hover')\"";
					O += "<span class=\"mini-tab-close\" " + _ + "></span>"
				}
				O += "</td>";
				if(J != F - 1) O += "<td class=\"mini-tabs-space2\"><div></div></td>"
			}
			O += "<td class=\"mini-tabs-space mini-tabs-lastSpace\" ><div></div></td></tr></table>"
		}
		if(L) O += "</div>";
		O += "</div>";
		this.Y$Y();
		mini.prepend(this.E3, O);
		var H = this.E3;
		this.NL = H.firstChild.lastChild;
		if(L) {
			this.WZ6s = this.NL.parentNode.firstChild;
			this.RAG = this.NL.parentNode.childNodes[1]
		}
		switch(this[V6d]) {
		case "center":
			var K = this.NL.childNodes;
			for(J = 0, F = K.length; J < F; J++) {
				var C = K[J],
					D = C.getElementsByTagName("td");
				D[0].style.width = "50%";
				D[D.length - 1].style.width = "50%"
			}
			break;
		case "right":
			K = this.NL.childNodes;
			for(J = 0, F = K.length; J < F; J++) {
				C = K[J], D = C.getElementsByTagName("td");
				D[0].style.width = "100%"
			}
			break;
		case "fit":
			break;
		default:
			K = this.NL.childNodes;
			for(J = 0, F = K.length; J < F; J++) {
				C = K[J], D = C.getElementsByTagName("td");
				D[D.length - 1].style.width = "100%"
			}
			break
		}
	},
	Erk: function() {
		this.Uin();
		var $ = this.E3;
		mini.append($, $.firstChild);
		this.NL = $.lastChild
	},
	JFL: function() {
		var J = "<table cellspacing=\"0\" cellpadding=\"0\"><tr>",
			B = this.getTabRows();
		for(var H = 0, A = B.length; H < A; H++) {
			var F = B[H],
				C = "";
			if(A > 1 && H != A - 1) C = "mini-tabs-header2";
			J += "<td class=\"" + C + "\"><table class=\"mini-tabs-header\" cellspacing=\"0\" cellpadding=\"0\">";
			J += "<tr ><td class=\"mini-tabs-space mini-tabs-firstSpace\" ><div></div></td></tr>";
			for(var G = 0, D = F.length; G < D; G++) {
				var I = F[G],
					E = this.T6ao(I);
				if(!I.visible) continue;
				var $ = this.tabs.indexOf(I),
					C = I.headerCls || "";
				if(I.enabled == false) C += " mini-disabled";
				J += "<tr><td id=\"" + E + "\" index=\"" + $ + "\"  class=\"mini-tab " + C + "\" style=\"" + I.headerStyle + "\">";
				if(I.iconCls || I[DP]) J += "<span class=\"mini-tab-icon " + I.iconCls + "\" style=\"" + I[DP] + "\"></span>";
				J += "<span class=\"mini-tab-text\">" + I.title + "</span>";
				if(I[$KS]) {
					var _ = "";
					if(I.enabled) _ = "onmouseover=\"V7(this,'mini-tab-close-hover')\" onmouseout=\"HMT(this,'mini-tab-close-hover')\"";
					J += "<span class=\"mini-tab-close\" " + _ + "></span>"
				}
				J += "</td></tr>";
				if(G != D - 1) J += "<tr><td class=\"mini-tabs-space2\"><div></div></td></tr>"
			}
			J += "<tr ><td class=\"mini-tabs-space mini-tabs-lastSpace\" ><div></div></td></tr>";
			J += "</table></td>"
		}
		J += "</tr ></table>";
		this.Y$Y();
		V7(this.NA, "mini-tabs-header");
		mini.append(this.NA, J);
		this.NL = this.NA
	},
	DDf: function() {
		this.JFL();
		HMT(this.NA, "mini-tabs-header");
		HMT(this.Y81M, "mini-tabs-header");
		mini.append(this.Y81M, this.NA.firstChild);
		this.NL = this.Y81M
	},
	Ita: function(_, $) {
		var A = {
			tab: _,
			index: this.tabs.indexOf(_),
			name: _.name.toLowerCase(),
			htmlEvent: $,
			cancel: false
		};
		this.fire("beforecloseclick", A);
		if(A.cancel == true) return;
		_.removeAction = "close";
		this.removeTab(_);
		this.fire("closeclick", A)
	},
	onBeforeCloseClick: function(_, $) {
		this.on("beforecloseclick", _, $)
	},
	onCloseClick: function(_, $) {
		this.on("closeclick", _, $)
	},
	onActiveChanged: function(_, $) {
		this.on("activechanged", _, $)
	},
	getAttrs: function(B) {
		var F = Ck3[GR_][$gN][GkN](this, B);
		mini[ENl](B, F, ["tabAlign", "tabPosition", "bodyStyle", "onactivechanged", "onbeforeactivechanged", "url", "ontabload", "ontabdestroy", "onbeforecloseclick", "oncloseclick", "titleField", "urlField", "nameField", "loadingMsg"]);
		mini[XD9s](B, F, ["allowAnim", "showBody", "maskOnLoad"]);
		mini[GGt](B, F, ["activeIndex"]);
		var A = [],
			E = mini[OIAh](B);
		for(var _ = 0, D = E.length; _ < D; _++) {
			var C = E[_],
				$ = {};
			A.push($);
			$.style = C.style.cssText;
			mini[ENl](C, $, ["name", "title", "url", "cls", "iconCls", "iconStyle", "headerCls", "headerStyle", "bodyCls", "bodyStyle", "onload", "ondestroy"]);
			mini[XD9s](C, $, ["newLine", "visible", "enabled", "showCloseButton", "refreshOnClick"]);
			$.bodyParent = C
		}
		F.tabs = A;
		return F
	}
});
PC7(Ck3, "tabs");
PF8P = function() {
	this.items = [];
	PF8P[GR_][KNT][GkN](this)
};
Iov(PF8P, FIa);
mini.copyTo(PF8P.prototype, _s_prototype);
var _s_prototype_hide = _s_prototype.hide;
mini.copyTo(PF8P.prototype, {
	width: 140,
	vertical: true,
	allowSelectItem: false,
	QrG: null,
	_O2q: "mini-menuitem-selected",
	textField: "text",
	resultAsTree: false,
	idField: "id",
	parentField: "pid",
	itemsField: "children",
	_clearBorder: false,
	showAction: "none",
	hideAction: "outerclick",
	getbyName: function(C) {
		for(var _ = 0, B = this.items.length; _ < B; _++) {
			var $ = this.items[_];
			if($.name == C) return $;
			if($.menu) {
				var A = $.menu.getbyName(C);
				if(A) return A
			}
		}
		return null
	},
	set: function($) {
		if(typeof $ == "string") return this;
		var _ = $.url;
		delete $.url;
		PF8P[GR_].set[GkN](this, $);
		if(_) this.setUrl(_);
		return this
	},
	uiCls: "mini-menu",
	_create: function() {
		var _ = "<table class=\"mini-menu\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"text-align:left;vertical-align:top;padding:0;border:0;\"><div class=\"mini-menu-inner\"></div></td></tr></table>",
			$ = document.createElement("div");
		$.innerHTML = _;
		this.el = $.firstChild;
		this._contentEl = mini.byClass("mini-menu-inner", this.el);
		if(this.isVertical() == false) V7(this.el, "mini-menu-horizontal")
	},
	destroy: function($) {
		this._popupEl = this.popupEl = null;
		this.owner = null;
		M4(document, "mousedown", this.OvM, this);
		M4(window, "resize", this.SA6y, this);
		PF8P[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(document, "mousedown", this.OvM, this);
			$v9(this.el, "mouseover", this.LZR, this);
			$v9(window, "resize", this.SA6y, this);
			$v9(this.el, "contextmenu", function($) {
				$.preventDefault()
			}, this)
		}, this)
	},
	within: function(B) {
		if(TWAc(this.el, B.target)) return true;
		for(var _ = 0, A = this.items.length; _ < A; _++) {
			var $ = this.items[_];
			if($[LU](B)) return true
		}
		return false
	},
	YItm: function() {
		if(!this._clearEl) this._clearEl = mini.append(this._contentEl, "<div style=\"clear:both;\"></div>");
		return this._clearEl
	},
	setVertical: function($) {
		this.vertical = $;
		if(!$) V7(this.el, "mini-menu-horizontal");
		else HMT(this.el, "mini-menu-horizontal");
		mini.append(this._contentEl, this.YItm())
	},
	getVertical: function() {
		return this.vertical
	},
	isVertical: function() {
		return this.vertical
	},
	show: function() {
		this[YKu](true)
	},
	hide: function() {
		this.hideItems();
		_s_prototype_hide[GkN](this)
	},
	hideItems: function() {
		for(var $ = 0, A = this.items.length; $ < A; $++) {
			var _ = this.items[$];
			_.hideMenu()
		}
	},
	showItemMenu: function($) {
		for(var _ = 0, B = this.items.length; _ < B; _++) {
			var A = this.items[_];
			if(A == $) A.showMenu();
			else A.hideMenu()
		}
	},
	hasShowItemMenu: function() {
		for(var $ = 0, A = this.items.length; $ < A; $++) {
			var _ = this.items[$];
			if(_ && _.menu && _.menu.isPopup) return true
		}
		return false
	},
	setItems: function(_) {
		if(!mini.isArray(_)) return;
		for(var $ = 0, A = _.length; $ < A; $++) this.addItem(_[$])
	},
	getItems: function() {
		return this.items
	},
	addItem: function($) {
		if($ == "-" || $ == "|") {
			mini.append(this._contentEl, "<span class=\"mini-separator\"></span>");
			return
		}
		if(!mini.isControl($) && !mini.getClass($.type)) $.type = "menuitem";
		$ = mini.getAndCreate($);
		this.items.push($);
		this._contentEl.appendChild($.el);
		$.ownerMenu = this;
		mini.append(this._contentEl, this.YItm());
		this.fire("itemschanged")
	},
	removeItem: function($) {
		$ = mini.get($);
		if(!$) return;
		this.items.remove($);
		this._contentEl.removeChild($.el);
		this.fire("itemschanged")
	},
	removeItemAt: function(_) {
		var $ = this.items[_];
		this.removeItem($)
	},
	removeAll: function() {
		var $ = this.items;
		for(var _ = items.length - 1; _ < l; _++) this.removeItem(items[_])
	},
	getGroupItems: function(C) {
		if(!C) return [];
		var A = [];
		for(var _ = 0, B = this.items.length; _ < B; _++) {
			var $ = this.items[_];
			if($[YSqP] == C) A.push($)
		}
		return A
	},
	getItem: function($) {
		if(typeof $ == "number") return this.items[$];
		return $
	},
	setAllowSelectItem: function($) {
		this.allowSelectItem = $
	},
	getAllowSelectItem: function() {
		return this.allowSelectItem
	},
	setSelectedItem: function($) {
		$ = this[GmG]($);
		this._OnItemSelect($)
	},
	getSelectedItem: function($) {
		return this.QrG
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setResultAsTree: function($) {
		this[ATIK] = $
	},
	getResultAsTree: function() {
		return this[ATIK]
	},
	setIdField: function($) {
		this[OzAi] = $
	},
	getIdField: function() {
		return this[OzAi]
	},
	setParentField: function($) {
		this[Rcs] = $
	},
	getParentField: function() {
		return this[Rcs]
	},
	url: "",
	YqFk: function() {
		var B = mini.getData(this.url);
		if(!B) B = [];
		if(this[ATIK] == false) B = mini.arrayToTree(B, this.itemsField, this.idField, this[Rcs]);
		var _ = mini[S6d](B, this.itemsField, this.idField, this[Rcs]);
		for(var A = 0, C = _.length; A < C; A++) {
			var $ = _[A];
			$.text = $[this.textField]
		}
		this.setItems(B);
		this.fire("load")
	},
	load: function($) {
		if(typeof $ == "string") this.setUrl($);
		else this.setItems($)
	},
	setUrl: function($) {
		this.url = $;
		this.YqFk()
	},
	getUrl: function() {
		return this.url
	},
	_OnItemClick: function($, _) {
		var A = {
			item: $,
			isLeaf: !$.menu,
			htmlEvent: _
		};
		if(this.isPopup) this.hide();
		else this.hideItems();
		if(this.allowSelectItem) this.setSelectedItem($);
		this.fire("itemclick", A);
		if(this.ownerItem);
	},
	_OnItemSelect: function($) {
		if(this.QrG) this.QrG[NeI](this._O2q);
		this.QrG = $;
		if(this.QrG) this.QrG[QlR](this._O2q);
		var _ = {
			item: this.QrG
		};
		this.fire("itemselect", _)
	},
	onItemClick: function(_, $) {
		this.on("itemclick", _, $)
	},
	onItemSelect: function(_, $) {
		this.on("itemselect", _, $)
	},
	parseItems: function(G) {
		var C = [];
		for(var _ = 0, F = G.length; _ < F; _++) {
			var B = G[_];
			if(B.className == "separator") {
				C.add("-");
				continue
			}
			var E = mini[OIAh](B),
				A = E[0],
				D = E[1],
				$ = new Oy2();
			if(!D) {
				mini.applyTo[GkN]($, B);
				C.add($);
				continue
			}
			mini.applyTo[GkN]($, A);
			$[Ivp](document.body);
			var H = new PF8P();
			mini.applyTo[GkN](H, D);
			$.setMenu(H);
			H[Ivp](document.body);
			C.add($)
		}
		return C.clone()
	},
	getAttrs: function(_) {
		var E = PF8P[GR_][$gN][GkN](this, _),
			D = jQuery(_);
		mini[ENl](_, E, ["popupEl", "popupCls", "showAction", "hideAction", "hAlign", "vAlign", "modalStyle", "onbeforeopen", "open", "onbeforeclose", "onclose", "url", "onitemclick", "onitemselect", "textField", "idField", "parentField"]);
		mini[XD9s](_, E, ["resultAsTree"]);
		var A = mini[OIAh](_),
			$ = this.parseItems(A);
		if($.length > 0) E.items = $;
		var B = D.attr("vertical");
		if(B) E.vertical = B == "true" ? true : false;
		var C = D.attr("allowSelectItem");
		if(C) E.allowSelectItem = C == "true" ? true : false;
		return E
	}
});
PC7(PF8P, "menu");
PF8PBar = function() {
	PF8PBar[GR_][KNT][GkN](this)
};
Iov(PF8PBar, PF8P, {
	uiCls: "mini-menubar",
	vertical: false,
	setVertical: function($) {
		this.vertical = false
	}
});
PC7(PF8PBar, "menubar");
mini.ContextMenu = function() {
	mini.ContextMenu[GR_][KNT][GkN](this)
};
Iov(mini.ContextMenu, PF8P, {
	uiCls: "mini-contextmenu",
	vertical: true,
	visible: false,
	setVertical: function($) {
		this.vertical = true
	}
});
PC7(mini.ContextMenu, "contextmenu");
Oy2 = function() {
	Oy2[GR_][KNT][GkN](this)
};
Iov(Oy2, FIa, {
	text: "",
	iconCls: "",
	iconStyle: "",
	iconPosition: "left",
	showIcon: true,
	showAllow: true,
	checked: false,
	checkOnClick: false,
	groupName: "",
	_hoverCls: "mini-menuitem-hover",
	NOw: "mini-menuitem-pressed",
	UFC: "mini-menuitem-checked",
	_clearBorder: false,
	menu: null,
	uiCls: "mini-menuitem",
	_create: function() {
		var $ = this.el = document.createElement("div");
		this.el.className = "mini-menuitem";
		this.el.innerHTML = "<div class=\"mini-menuitem-inner\"><div class=\"mini-menuitem-icon\"></div><div class=\"mini-menuitem-text\"></div><div class=\"mini-menuitem-allow\"></div></div>";
		this.VHBX = this.el.firstChild;
		this.RRre = this.VHBX.firstChild;
		this.IJ6 = this.VHBX.childNodes[1];
		this.allowEl = this.VHBX.lastChild
	},
	_initEvents: function() {
		$v9(this.el, "click", this.PGY, this);
		$v9(this.el, "mouseup", this.Hfq, this);
		$v9(this.el, "mouseover", this.LZR, this);
		$v9(this.el, "mouseout", this.$MHa, this)
	},
	destroy: function($) {
		this.menu = null;
		Oy2[GR_][EqU5][GkN](this, $)
	},
	within: function($) {
		if(TWAc(this.el, $.target)) return true;
		if(this.menu && this.menu[LU]($)) return true;
		return false
	},
	doUpdate: function() {
		if(this.IJ6) this.IJ6.innerHTML = this.text;
		if(this.RRre) {
			Gn(this.RRre, this[DP]);
			V7(this.RRre, this.iconCls);
			this.RRre.style.display = (this[DP] || this.iconCls) ? "block" : "none"
		}
		if(this.iconPosition == "top") V7(this.el, "mini-menuitem-icontop");
		else HMT(this.el, "mini-menuitem-icontop");
		if(this.checked) V7(this.el, this.UFC);
		else HMT(this.el, this.UFC);
		if(this.allowEl) if(this.menu && this.menu.items.length > 0) this.allowEl.style.display = "block";
		else this.allowEl.style.display = "none"
	},
	setText: function($) {
		this.text = $;
		this[Mdk]()
	},
	getText: function() {
		return this.text
	},
	setIconCls: function($) {
		HMT(this.RRre, this.iconCls);
		this.iconCls = $;
		this[Mdk]()
	},
	getIconCls: function() {
		return this.iconCls
	},
	setIconStyle: function($) {
		this[DP] = $;
		this[Mdk]()
	},
	getIconStyle: function() {
		return this[DP]
	},
	setIconPosition: function($) {
		this.iconPosition = $;
		this[Mdk]()
	},
	getIconPosition: function() {
		return this.iconPosition
	},
	setCheckOnClick: function($) {
		this[UmO] = $;
		if($) V7(this.el, "mini-menuitem-showcheck");
		else HMT(this.el, "mini-menuitem-showcheck")
	},
	getCheckOnClick: function() {
		return this[UmO]
	},
	setChecked: function($) {
		if(this.checked != $) {
			this.checked = $;
			this[Mdk]();
			this.fire("checkedchanged")
		}
	},
	getChecked: function() {
		return this.checked
	},
	setGroupName: function($) {
		if(this[YSqP] != $) this[YSqP] = $
	},
	getGroupName: function() {
		return this[YSqP]
	},
	setChildren: function($) {
		this.setMenu($)
	},
	setMenu: function($) {
		if(mini.isArray($)) $ = {
			type: "menu",
			items: $
		};
		if(this.menu !== $) {
			this.menu = mini.getAndCreate($);
			this.menu.hide();
			this.menu.ownerItem = this;
			this[Mdk]();
			this.menu.on("itemschanged", this.A_J, this)
		}
	},
	getMenu: function() {
		return this.menu
	},
	showMenu: function() {
		if(this.menu) {
			this.menu.setHideAction("outerclick");
			var $ = {
				hAlign: "outright",
				vAlign: "top",
				outHAlign: "outleft",
				popupCls: "mini-menu-popup"
			};
			if(this.ownerMenu && this.ownerMenu.vertical == false) {
				$.hAlign = "left";
				$.vAlign = "below";
				$.outHAlign = null
			}
			this.menu.showAtEl(this.el, $)
		}
	},
	hideMenu: function() {
		if(this.menu) this.menu.hide()
	},
	hide: function() {
		this.hideMenu();
		this[YKu](false)
	},
	A_J: function($) {
		this[Mdk]()
	},
	getTopMenu: function() {
		if(this.ownerMenu) if(this.ownerMenu.ownerItem) return this.ownerMenu.ownerItem.getTopMenu();
		else return this.ownerMenu;
		return null
	},
	PGY: function(D) {
		if(this[RBE]()) return;
		if(this[UmO]) if(this.ownerMenu && this[YSqP]) {
			var B = this.ownerMenu.getGroupItems(this[YSqP]);
			if(B.length > 0) {
				if(this.checked == false) {
					for(var _ = 0, C = B.length; _ < C; _++) {
						var $ = B[_];
						if($ != this) $.setChecked(false)
					}
					this.setChecked(true)
				}
			} else this.setChecked(!this.checked)
		} else this.setChecked(!this.checked);
		this.fire("click");
		var A = this.getTopMenu();
		if(A) A._OnItemClick(this, D)
	},
	Hfq: function(_) {
		if(this[RBE]()) return;
		if(this.ownerMenu) {
			var $ = this;
			setTimeout(function() {
				if($[WH4]()) $.ownerMenu.showItemMenu($)
			}, 1)
		}
	},
	LZR: function($) {
		if(this[RBE]()) return;
		V7(this.el, this._hoverCls);
		if(this.ownerMenu) if(this.ownerMenu.isVertical() == true) this.ownerMenu.showItemMenu(this);
		else if(this.ownerMenu.hasShowItemMenu()) this.ownerMenu.showItemMenu(this)
	},
	$MHa: function($) {
		HMT(this.el, this._hoverCls)
	},
	onClick: function(_, $) {
		this.on("click", _, $)
	},
	onCheckedChanged: function(_, $) {
		this.on("checkedchanged", _, $)
	},
	getAttrs: function($) {
		var A = Oy2[GR_][$gN][GkN](this, $),
			_ = jQuery($);
		A.text = $.innerHTML;
		mini[ENl]($, A, ["text", "iconCls", "iconStyle", "iconPosition", "groupName", "onclick", "oncheckedchanged"]);
		mini[XD9s]($, A, ["checkOnClick", "checked"]);
		return A
	}
});
PC7(Oy2, "menuitem");
QF30 = function() {
	this.Skl();
	QF30[GR_][KNT][GkN](this)
};
Iov(QF30, FIa, {
	width: 180,
	activeIndex: -1,
	autoCollapse: false,
	groupCls: "",
	groupStyle: "",
	groupHeaderCls: "",
	groupHeaderStyle: "",
	groupBodyCls: "",
	groupBodyStyle: "",
	groupHoverCls: "",
	groupActiveCls: "",
	allowAnim: true,
	set: function(A) {
		if(typeof A == "string") return this;
		var $ = this.ERW;
		this.ERW = false;
		var _ = A.activeIndex;
		delete A.activeIndex;
		QF30[GR_].set[GkN](this, A);
		if(mini.isNumber(_)) this.setActiveIndex(_);
		this.ERW = $;
		this[Fcv]();
		return this
	},
	uiCls: "mini-outlookbar",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-outlookbar";
		this.el.innerHTML = "<div class=\"mini-outlookbar-border\"></div>";
		this.TG = this.el.firstChild
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this)
		}, this)
	},
	NH: function($) {
		return this.uid + "$" + $._id
	},
	_GroupId: 1,
	Skl: function() {
		this.groups = []
	},
	CYgm: function(_) {
		var H = this.NH(_),
			G = "<div id=\"" + H + "\" class=\"mini-outlookbar-group " + _.cls + "\" style=\"" + _.style + "\">" + "<div class=\"mini-outlookbar-groupHeader " + _.headerCls + "\" style=\"" + _.headerStyle + ";\"></div>" + "<div class=\"mini-outlookbar-groupBody " + _.bodyCls + "\" style=\"" + _.bodyStyle + ";\"></div>" + "</div>",
			A = mini.append(this.TG, G),
			E = A.lastChild,
			C = _.body;
		delete _.body;
		if(C) {
			if(!mini.isArray(C)) C = [C];
			for(var $ = 0, F = C.length; $ < F; $++) {
				var B = C[$];
				mini.append(E, B)
			}
			C.length = 0
		}
		if(_.bodyParent) {
			var D = _.bodyParent;
			while(D.firstChild) E.appendChild(D.firstChild)
		}
		delete _.bodyParent;
		return A
	},
	createGroup: function(_) {
		var $ = mini.copyTo({
			_id: this._GroupId++,
			name: "",
			title: "",
			cls: "",
			style: "",
			iconCls: "",
			iconStyle: "",
			headerCls: "",
			headerStyle: "",
			bodyCls: "",
			bodyStyle: "",
			visible: true,
			enabled: true,
			showCollapseButton: true,
			expanded: false
		}, _);
		return $
	},
	setGroups: function(_) {
		if(!mini.isArray(_)) return;
		this.removeAll();
		for(var $ = 0, A = _.length; $ < A; $++) this.addGroup(_[$])
	},
	getGroups: function() {
		return this.groups
	},
	addGroup: function(_, $) {
		if(typeof _ == "string") _ = {
			title: _
		};
		_ = this.createGroup(_);
		if(typeof $ != "number") $ = this.groups.length;
		this.groups.insert($, _);
		var B = this.CYgm(_);
		_._el = B;
		var $ = this.groups.indexOf(_),
			A = this.groups[$ + 1];
		if(A) {
			var C = this.getGroupEl(A);
			jQuery(C).before(B)
		}
		this[Mdk]();
		return _
	},
	updateGroup: function($, _) {
		var $ = this.getGroup($);
		if(!$) return;
		mini.copyTo($, _);
		this[Mdk]()
	},
	removeGroup: function($) {
		$ = this.getGroup($);
		if(!$) return;
		var _ = this.getGroupEl($);
		if(_) _.parentNode.removeChild(_);
		this.groups.remove($);
		this[Mdk]()
	},
	removeAll: function() {
		for(var $ = this.groups.length - 1; $ >= 0; $--) this.removeGroup($)
	},
	moveGroup: function(_, $) {
		_ = this.getGroup(_);
		if(!_) return;
		target = this.getGroup($);
		var A = this.getGroupEl(_);
		this.groups.remove(_);
		if(target) {
			$ = this.groups.indexOf(target);
			this.groups.insert($, _);
			var B = this.getGroupEl(target);
			jQuery(B).before(A)
		} else {
			this.groups.add(_);
			this.TG.appendChild(A)
		}
		this[Mdk]()
	},
	doUpdate: function() {
		for(var _ = 0, E = this.groups.length; _ < E; _++) {
			var A = this.groups[_],
				B = A._el,
				D = B.firstChild,
				C = B.lastChild,
				$ = "<div class=\"mini-outlookbar-icon " + A.iconCls + "\" style=\"" + A[DP] + ";\"></div>",
				F = "<div class=\"mini-tools\"><span class=\"mini-tools-collapse\"></span></div>" + ((A[DP] || A.iconCls) ? $ : "") + "<div class=\"mini-outlookbar-groupTitle\">" + A.title + "</div><div style=\"clear:both;\"></div>";
			D.innerHTML = F;
			if(A.enabled) HMT(B, "mini-disabled");
			else V7(B, "mini-disabled");
			V7(B, A.cls);
			Gn(B, A.style);
			V7(C, A.bodyCls);
			Gn(C, A.bodyStyle);
			V7(D, A.headerCls);
			Gn(D, A.headerStyle);
			HMT(B, "mini-outlookbar-firstGroup");
			HMT(B, "mini-outlookbar-lastGroup");
			if(_ == 0) V7(B, "mini-outlookbar-firstGroup");
			if(_ == E - 1) V7(B, "mini-outlookbar-lastGroup")
		}
		this[Fcv]()
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		if(this.Ms3) return;
		this.Ji7();
		for(var $ = 0, H = this.groups.length; $ < H; $++) {
			var _ = this.groups[$],
				B = _._el,
				D = B.lastChild;
			if(_.expanded) {
				V7(B, "mini-outlookbar-expand");
				HMT(B, "mini-outlookbar-collapse")
			} else {
				HMT(B, "mini-outlookbar-expand");
				V7(B, "mini-outlookbar-collapse")
			}
			D.style.height = "auto";
			D.style.display = _.expanded ? "block" : "none";
			B.style.display = _.visible ? "" : "none";
			var A = R0(B, true),
				E = VO(D),
				G = D0uu(D);
			if(jQuery.boxModel) A = A - E.left - E.right - G.left - G.right;
			D.style.width = A + "px"
		}
		var F = this[_H](),
			C = this.getActiveGroup();
		if(!F && this[PV] && C) {
			B = this.getGroupEl(this.activeIndex);
			B.lastChild.style.height = this.J5y() + "px"
		}
		mini.layout(this.TG)
	},
	Ji7: function() {
		if(this[_H]()) this.TG.style.height = "auto";
		else {
			var $ = this[DTTy](true);
			if(!jQuery.boxModel) {
				var _ = D0uu(this.TG);
				$ = $ + _.top + _.bottom
			}
			if($ < 0) $ = 0;
			this.TG.style.height = $ + "px"
		}
	},
	J5y: function() {
		var C = jQuery(this.el).height(),
			K = D0uu(this.TG);
		C = C - K.top - K.bottom;
		var A = this.getActiveGroup(),
			E = 0;
		for(var F = 0, D = this.groups.length; F < D; F++) {
			var _ = this.groups[F],
				G = this.getGroupEl(_);
			if(_.visible == false || _ == A) continue;
			var $ = G.lastChild.style.display;
			G.lastChild.style.display = "none";
			var J = jQuery(G).outerHeight();
			G.lastChild.style.display = $;
			var L = TA$(G);
			J = J + L.top + L.bottom;
			E += J
		}
		C = C - E;
		var H = this.getGroupEl(this.activeIndex);
		C = C - jQuery(H.firstChild).outerHeight();
		if(jQuery.boxModel) {
			var B = VO(H.lastChild),
				I = D0uu(H.lastChild);
			C = C - B.top - B.bottom - I.top - I.bottom
		}
		B = VO(H), I = D0uu(H), L = TA$(H);
		C = C - L.top - L.bottom;
		C = C - B.top - B.bottom - I.top - I.bottom;
		if(C < 0) C = 0;
		return C
	},
	getGroup: function($) {
		if(typeof $ == "object") return $;
		if(typeof $ == "number") return this.groups[$];
		else for(var _ = 0, B = this.groups.length; _ < B; _++) {
			var A = this.groups[_];
			if(A.name == $) return A
		}
	},
	Ap16: function(B) {
		for(var $ = 0, A = this.groups.length; $ < A; $++) {
			var _ = this.groups[$];
			if(_._id == B) return _
		}
	},
	getGroupEl: function($) {
		var _ = this.getGroup($);
		if(!_) return null;
		return _._el
	},
	getGroupBodyEl: function($) {
		var _ = this.getGroupEl($);
		if(_) return _.lastChild;
		return null
	},
	setAutoCollapse: function($) {
		this[PV] = $
	},
	getAutoCollapse: function() {
		return this[PV]
	},
	setActiveIndex: function(_) {
		var $ = this.getGroup(_),
			A = this.getGroup(this.activeIndex),
			B = $ != A;
		if($) this.activeIndex = this.groups.indexOf($);
		else this.activeIndex = -1;
		$ = this.getGroup(this.activeIndex);
		if($) {
			var C = this.allowAnim;
			this.allowAnim = false;
			this.expandGroup($);
			this.allowAnim = C
		}
	},
	getActiveIndex: function() {
		return this.activeIndex
	},
	getActiveGroup: function() {
		return this.getGroup(this.activeIndex)
	},
	showGroup: function($) {
		$ = this.getGroup($);
		if(!$ || $.visible == true) return;
		$.visible = true;
		this[Mdk]()
	},
	hideGroup: function($) {
		$ = this.getGroup($);
		if(!$ || $.visible == false) return;
		$.visible = false;
		this[Mdk]()
	},
	toggleGroup: function($) {
		$ = this.getGroup($);
		if(!$) return;
		if($.expanded) this.collapseGroup($);
		else this.expandGroup($)
	},
	collapseGroup: function(_) {
		_ = this.getGroup(_);
		if(!_ || _.expanded == false) return;
		var D = _.expanded,
			E = 0;
		if(this[PV] && !this[_H]()) E = this.J5y();
		var F = false;
		_.expanded = false;
		var $ = this.groups.indexOf(_);
		if($ == this.activeIndex) {
			this.activeIndex = -1;
			F = true
		}
		var C = this.getGroupBodyEl(_);
		if(this.allowAnim && D) {
			this.Ms3 = true;
			C.style.display = "block";
			C.style.height = "auto";
			if(this[PV] && !this[_H]()) C.style.height = E + "px";
			var A = {
				height: "1px"
			};
			V7(C, "mini-outlookbar-overflow");
			var B = this,
				H = jQuery(C);
			H.animate(A, 180, function() {
				B.Ms3 = false;
				HMT(C, "mini-outlookbar-overflow");
				B[Fcv]()
			})
		} else this[Fcv]();
		var G = {
			group: _,
			index: this.groups.indexOf(_),
			name: _.name
		};
		this.fire("Collapse", G);
		if(F) this.fire("activechanged")
	},
	expandGroup: function($) {
		$ = this.getGroup($);
		if(!$ || $.expanded) return;
		var H = $.expanded;
		$.expanded = true;
		this.activeIndex = this.groups.indexOf($);
		fire = true;
		if(this[PV]) for(var D = 0, B = this.groups.length; D < B; D++) {
			var C = this.groups[D];
			if(C.expanded && C != $) this.collapseGroup(C)
		}
		var G = this.getGroupBodyEl($);
		if(this.allowAnim && H == false) {
			this.Ms3 = true;
			G.style.display = "block";
			if(this[PV] && !this[_H]()) {
				var A = this.J5y();
				G.style.height = (A) + "px"
			} else G.style.height = "auto";
			var _ = KwY(G);
			G.style.height = "1px";
			var E = {
				height: _ + "px"
			},
				I = G.style.overflow;
			G.style.overflow = "hidden";
			V7(G, "mini-outlookbar-overflow");
			var F = this,
				K = jQuery(G);
			K.animate(E, 180, function() {
				G.style.overflow = I;
				HMT(G, "mini-outlookbar-overflow");
				F.Ms3 = false;
				F[Fcv]()
			})
		} else this[Fcv]();
		var J = {
			group: $,
			index: this.groups.indexOf($),
			name: $.name
		};
		this.fire("Expand", J);
		if(fire) this.fire("activechanged")
	},
	$TBF: function($) {
		$ = this.getGroup($);
		var _ = {
			group: $,
			groupIndex: this.groups.indexOf($),
			groupName: $.name,
			cancel: false
		};
		if($.expanded) {
			this.fire("BeforeCollapse", _);
			if(_.cancel == false) this.collapseGroup($)
		} else {
			this.fire("BeforeExpand", _);
			if(_.cancel == false) this.expandGroup($)
		}
	},
	NhT: function(B) {
		var _ = ZW(B.target, "mini-outlookbar-group");
		if(!_) return null;
		var $ = _.id.split("$"),
			A = $[$.length - 1];
		return this.Ap16(A)
	},
	PGY: function(A) {
		if(this.Ms3) return;
		var _ = ZW(A.target, "mini-outlookbar-groupHeader");
		if(!_) return;
		var $ = this.NhT(A);
		if(!$) return;
		this.$TBF($)
	},
	parseGroups: function(D) {
		var A = [];
		for(var $ = 0, C = D.length; $ < C; $++) {
			var B = D[$],
				_ = {};
			A.push(_);
			_.style = B.style.cssText;
			mini[ENl](B, _, ["name", "title", "cls", "iconCls", "iconStyle", "headerCls", "headerStyle", "bodyCls", "bodyStyle"]);
			mini[XD9s](B, _, ["visible", "enabled", "showCollapseButton", "expanded"]);
			_.bodyParent = B
		}
		return A
	},
	getAttrs: function($) {
		var A = QF30[GR_][$gN][GkN](this, $);
		mini[ENl]($, A, ["onactivechanged", "oncollapse", "onexpand"]);
		mini[XD9s]($, A, ["autoCollapse", "allowAnim"]);
		mini[GGt]($, A, ["activeIndex"]);
		var _ = mini[OIAh]($);
		A.groups = this.parseGroups(_);
		return A
	}
});
PC7(QF30, "outlookbar");
UeH$ = function() {
	UeH$[GR_][KNT][GkN](this);
	this.data = []
};
Iov(UeH$, QF30, {
	url: "",
	textField: "text",
	iconField: "iconCls",
	urlField: "url",
	resultAsTree: false,
	itemsField: "children",
	idField: "id",
	parentField: "pid",
	style: "width:100%;height:100%;",
	set: function(_) {
		if(typeof _ == "string") return this;
		var A = _.url;
		delete _.url;
		var $ = _.activeIndex;
		delete _.activeIndex;
		UeH$[GR_].set[GkN](this, _);
		if(A) this.setUrl(A);
		if(mini.isNumber($)) this.setActiveIndex($);
		return this
	},
	uiCls: "mini-outlookmenu",
	destroy: function(B) {
		if(this.Clg) {
			var _ = this.Clg.clone();
			for(var $ = 0, C = _.length; $ < C; $++) {
				var A = _[$];
				A[EqU5]()
			}
			this.Clg.length = 0
		}
		UeH$[GR_][EqU5][GkN](this, B)
	},
	YqFk: function() {
		var B = mini.getData(this.url);
		if(!B) B = [];
		if(this[ATIK] == false) B = mini.arrayToTree(B, this.itemsField, this.idField, this[Rcs]);
		var _ = mini[S6d](B, this.itemsField, this.idField, this[Rcs]);
		for(var A = 0, C = _.length; A < C; A++) {
			var $ = _[A];
			$.text = $[this.textField];
			$.url = $[this.urlField];
			$.iconCls = $[this.iconField]
		}
		this.createNavBarMenu(B);
		this.fire("load")
	},
	load: function($) {
		if(typeof $ == "string") this.setUrl($);
		else this.createNavBarMenu($)
	},
	setUrl: function($) {
		this.url = $;
		this.YqFk()
	},
	getUrl: function() {
		return this.url
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setIconField: function($) {
		this.iconField = $
	},
	getIconField: function() {
		return this.iconField
	},
	setUrlField: function($) {
		this[PP] = $
	},
	getUrlField: function() {
		return this[PP]
	},
	setResultAsTree: function($) {
		this[ATIK] = $
	},
	getResultAsTree: function() {
		return this[ATIK]
	},
	setNodesField: function($) {
		this.nodesField = $
	},
	getNodesField: function() {
		return this.nodesField
	},
	setIdField: function($) {
		this[OzAi] = $
	},
	getIdField: function() {
		return this[OzAi]
	},
	setParentField: function($) {
		this[Rcs] = $
	},
	getParentField: function() {
		return this[Rcs]
	},
	YF: null,
	getSelected: function() {
		return this.YF
	},
	getAttrs: function($) {
		var _ = UeH$[GR_][$gN][GkN](this, $);
		_.text = $.innerHTML;
		mini[ENl]($, _, ["url", "textField", "urlField", "idField", "parentField", "itemsField", "iconField", "onitemclick", "onitemselect"]);
		mini[XD9s]($, _, ["resultAsTree"]);
		return _
	},
	autoCollapse: true,
	activeIndex: 0,
	createNavBarMenu: function(D) {
		if(!mini.isArray(D)) D = [];
		this.data = D;
		var B = [];
		for(var _ = 0, E = this.data.length; _ < E; _++) {
			var $ = this.data[_],
				A = {};
			A.title = $.text;
			A.titleCls = $.iconCls;
			B.push(A);
			A._children = $[this.itemsField]
		}
		this.setGroups(B);
		this.setActiveIndex(this.activeIndex);
		this.Clg = [];
		for(_ = 0, E = this.groups.length; _ < E; _++) {
			var A = this.groups[_],
				C = this.getGroupBodyEl(A),
				F = new PF8P();
			F.set({
				style: "width:100%;height:100%;border:0;background:none",
				allowSelectItem: true,
				items: A._children
			});
			F[Ivp](C);
			F.on("itemclick", this.VsW, this);
			F.on("itemselect", this.OnA, this);
			this.Clg.push(F);
			delete A._children
		}
	},
	VsW: function(_) {
		var $ = {
			item: _.item,
			htmlEvent: _.htmlEvent
		};
		this.fire("itemclick", $)
	},
	OnA: function(C) {
		if(!C.item) return;
		for(var $ = 0, A = this.Clg.length; $ < A; $++) {
			var B = this.Clg[$];
			if(B != C.sender) B.setSelectedItem(null)
		}
		var _ = {
			item: C.item,
			htmlEvent: C.htmlEvent
		};
		this.YF = C.item;
		this.fire("itemselect", _)
	}
});
PC7(UeH$, "outlookmenu");
Eke = function() {
	Eke[GR_][KNT][GkN](this);
	this.data = []
};
Iov(Eke, QF30, {
	url: "",
	textField: "text",
	iconField: "iconCls",
	urlField: "url",
	resultAsTree: false,
	nodesField: "children",
	idField: "id",
	parentField: "pid",
	style: "width:100%;height:100%;",
	set: function(_) {
		if(typeof _ == "string") return this;
		var A = _.url;
		delete _.url;
		var $ = _.activeIndex;
		delete _.activeIndex;
		Eke[GR_].set[GkN](this, _);
		if(A) this.setUrl(A);
		if(mini.isNumber($)) this.setActiveIndex($);
		return this
	},
	uiCls: "mini-outlooktree",
	destroy: function(B) {
		if(this.EbJp) {
			var _ = this.EbJp.clone();
			for(var $ = 0, C = _.length; $ < C; $++) {
				var A = _[$];
				A[EqU5]()
			}
			this.EbJp.length = 0
		}
		Eke[GR_][EqU5][GkN](this, B)
	},
	YqFk: function() {
		var B = mini.getData(this.url);
		if(!B) B = [];
		if(this[ATIK] == false) B = mini.arrayToTree(B, this.nodesField, this.idField, this[Rcs]);
		var _ = mini[S6d](B, this.nodesField, this.idField, this[Rcs]);
		for(var A = 0, C = _.length; A < C; A++) {
			var $ = _[A];
			$.text = $[this.textField];
			$.url = $[this.urlField];
			$.iconCls = $[this.iconField]
		}
		this.createNavBarTree(B);
		this.fire("load")
	},
	load: function($) {
		if(typeof $ == "string") this.setUrl($);
		else this.createNavBarTree($)
	},
	setUrl: function($) {
		this.url = $;
		this.YqFk()
	},
	getUrl: function() {
		return this.url
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setIconField: function($) {
		this.iconField = $
	},
	getIconField: function() {
		return this.iconField
	},
	setUrlField: function($) {
		this[PP] = $
	},
	getUrlField: function() {
		return this[PP]
	},
	setResultAsTree: function($) {
		this[ATIK] = $
	},
	getResultAsTree: function() {
		return this[ATIK]
	},
	setNodesField: function($) {
		this.nodesField = $
	},
	getNodesField: function() {
		return this.nodesField
	},
	setIdField: function($) {
		this[OzAi] = $
	},
	getIdField: function() {
		return this[OzAi]
	},
	setParentField: function($) {
		this[Rcs] = $
	},
	getParentField: function() {
		return this[Rcs]
	},
	YF: null,
	getSelected: function() {
		return this.YF
	},
	getAttrs: function($) {
		var _ = Eke[GR_][$gN][GkN](this, $);
		_.text = $.innerHTML;
		mini[ENl]($, _, ["url", "textField", "urlField", "idField", "parentField", "nodesField", "iconField", "onnodeclick", "onnodeselect"]);
		mini[XD9s]($, _, ["resultAsTree"]);
		return _
	},
	autoCollapse: true,
	activeIndex: 0,
	createNavBarTree: function(D) {
		if(!mini.isArray(D)) D = [];
		this.data = D;
		var B = [];
		for(var _ = 0, E = this.data.length; _ < E; _++) {
			var $ = this.data[_],
				A = {};
			A.title = $.text;
			A.titleCls = $.iconCls;
			B.push(A);
			A._children = $[this.nodesField]
		}
		this.setGroups(B);
		this.setActiveIndex(this.activeIndex);
		this.EbJp = [];
		for(_ = 0, E = this.groups.length; _ < E; _++) {
			var A = this.groups[_],
				C = this.getGroupBodyEl(A),
				D = new L2();
			D.set({
				showTreeIcon: true,
				style: "width:100%;height:100%;border:0;background:none",
				data: A._children
			});
			D[Ivp](C);
			D.on("nodeclick", this.HIv, this);
			D.on("nodeselect", this.Ov, this);
			this.EbJp.push(D);
			delete A._children
		}
	},
	HIv: function(_) {
		var $ = {
			node: _.node,
			isLeaf: _.sender.isLeaf(_.node),
			htmlEvent: _.htmlEvent
		};
		this.fire("nodeclick", $)
	},
	Ov: function(C) {
		if(!C.node) return;
		for(var $ = 0, B = this.EbJp.length; $ < B; $++) {
			var A = this.EbJp[$];
			if(A != C.sender) A.selectNode(null)
		}
		var _ = {
			node: C.node,
			isLeaf: C.sender.isLeaf(C.node),
			htmlEvent: C.htmlEvent
		};
		this.YF = C.node;
		this.fire("nodeselect", _)
	}
});
PC7(Eke, "outlooktree");
mini.NavBar = function() {
	mini.NavBar[GR_][KNT][GkN](this)
};
Iov(mini.NavBar, QF30, {
	uiCls: "mini-navbar"
});
PC7(mini.NavBar, "navbar");
mini.NavBarMenu = function() {
	mini.NavBarMenu[GR_][KNT][GkN](this)
};
Iov(mini.NavBarMenu, UeH$, {
	uiCls: "mini-navbarmenu"
});
PC7(mini.NavBarMenu, "navbarmenu");
mini.NavBarTree = function() {
	mini.NavBarTree[GR_][KNT][GkN](this)
};
Iov(mini.NavBarTree, Eke, {
	uiCls: "mini-navbartree"
});
PC7(mini.NavBarTree, "navbartree");
mini.ToolBar = function() {
	mini.ToolBar[GR_][KNT][GkN](this)
};
Iov(mini.ToolBar, FIa, {
	_clearBorder: false,
	style: "",
	uiCls: "mini-toolbar",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-toolbar"
	},
	_initEvents: function() {},
	doLayout: function() {
		if(!this.canLayout()) return;
		var A = mini[OIAh](this.el, true);
		for(var $ = 0, _ = A.length; $ < _; $++) mini.layout(A[$])
	},
	set_bodyParent: function($) {
		if(!$) return;
		this.el = $;
		this[Fcv]()
	},
	getAttrs: function($) {
		var _ = {};
		mini[ENl]($, _, ["id", "borderStyle"]);
		this.el = $;
		this.el.uid = this.uid;
		return _
	}
});
PC7(mini.ToolBar, "toolbar");
L2 = function($) {
	this.root = {
		_id: -1,
		_pid: "",
		_level: -1
	};
	this.root[this.nodesField] = [];
	this.IYa = {};
	this.Cn$ = {};
	this._viewNodes = null;
	L2[GR_][KNT][GkN](this, $);
	this.on("beforeexpand", function(B) {
		var $ = B.node,
			A = this.isLeaf($),
			_ = $[this.nodesField];
		if(!A && (!_ || _.length == 0)) {
			B.cancel = true;
			this.loadNode($)
		}
	}, this);
	this[Mdk]()
};
L2.NodeUID = 1;
var lastNodeLevel = [];
Iov(L2, FIa, {
	isTree: true,
	C4U: "block",
	expandOnDblClick: true,
	value: "",
	XLb: null,
	allowSelect: true,
	showCheckBox: false,
	showFolderCheckBox: true,
	showExpandButtons: true,
	enableHotTrack: true,
	showArrow: false,
	expandOnLoad: false,
	delimiter: ",",
	url: "",
	root: null,
	resultAsTree: true,
	parentField: "pid",
	idField: "id",
	textField: "text",
	iconField: "iconCls",
	nodesField: "children",
	showTreeIcon: false,
	showTreeLines: true,
	checkRecursive: false,
	allowAnim: true,
	Hm: "mini-tree-checkbox",
	I9W: "mini-tree-selectedNode",
	Oz: "mini-tree-node-hover",
	leafIcon: "mini-tree-leaf",
	folderIcon: "mini-tree-folder",
	Dqn: "mini-tree-border",
	OUH: "mini-tree-header",
	R7: "mini-tree-body",
	XMk: "mini-tree-node",
	GAj: "mini-tree-nodes",
	BI: "mini-tree-expand",
	Bd: "mini-tree-collapse",
	OH4l: "mini-tree-node-ecicon",
	CW: "mini-tree-nodeshow",
	set: function(A) {
		if(typeof A == "string") return this;
		var $ = A.value;
		delete A.value;
		var B = A.url;
		delete A.url;
		var _ = A.data;
		delete A.data;
		L2[GR_].set[GkN](this, A);
		if(!mini.isNull(_)) this[OUQ](_);
		if(!mini.isNull(B)) this.setUrl(B);
		if(!mini.isNull($)) this[UD7]($);
		return this
	},
	uiCls: "mini-tree",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-tree";
		if(this[Pi] == true) V7(this.el, "mini-tree-treeLine");
		this.el.style.display = "block";
		this.TG = mini.append(this.el, "<div class=\"" + this.Dqn + "\">" + "<div class=\"" + this.OUH + "\"></div><div class=\"" + this.R7 + "\"></div></div>");
		this.NL = this.TG.childNodes[0];
		this.H23I = this.TG.childNodes[1];
		this._DragDrop = new BPF(this)
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this);
			$v9(this.el, "dblclick", this.Esh, this);
			$v9(this.el, "mousedown", this.AOlf, this);
			$v9(this.el, "mousemove", this.VeS, this);
			$v9(this.el, "mouseout", this.$MHa, this)
		}, this)
	},
	load: function($) {
		if(typeof $ == "string") {
			this.url = $;
			this.YqFk({}, this.root)
		} else this[OUQ]($)
	},
	setData: function($) {
		this[M3b]($);
		this.data = $
	},
	getData: function() {
		return this.data
	},
	toArray: function() {
		return this.getList()
	},
	getList: function() {
		if(!this.CcdD) {
			this.CcdD = mini[S6d](this.root[this.nodesField], this.nodesField, this.idField, this.parentField, "-1");
			this._indexs = {};
			for(var $ = 0, A = this.CcdD.length; $ < A; $++) {
				var _ = this.CcdD[$];
				this._indexs[_[this.idField]] = $
			}
		}
		return this.CcdD
	},
	_clearTree: function() {
		this.CcdD = null;
		this._indexs = null
	},
	loadData: function($) {
		if(!mini.isArray($)) $ = [];
		this.root[this.nodesField] = $;
		this.QfsR(this.root, null);
		this.cascadeChild(this.root, function($) {
			if(mini.isNull($.expanded)) $.expanded = this.expandOnLoad
		}, this);
		this._viewNodes = null;
		this[Mdk]()
	},
	clearData: function() {
		this[M3b]([])
	},
	setUrl: function($) {
		this.url = $;
		this.load($)
	},
	getUrl: function() {
		return this.url
	},
	loadNode: function(C, $) {
		C = this[CyM](C);
		if(!C) return;
		if(this.isLeaf(C)) return;
		var B = {};
		B[this.idField] = this[Ez9](C);
		var _ = this;
		_[XiOJ](C, "mini-tree-loading");
		this.ajaxAsync = true;
		var A = new Date();
		this.YqFk(B, C, function(B) {
			var D = new Date() - A;
			if(D < 60) D = 60 - D;
			setTimeout(function() {
				_[GnY](C, "mini-tree-loading");
				_.removeNodes(C[_.nodesField]);
				if(B && B.length > 0) {
					_.addNodes(B, C);
					if($ !== false) _[LaOj](C, true);
					else _[XI8](C, true);
					_.fire("loadnode")
				} else {
					delete C.isLeaf;
					_.Pc22(C)
				}
			}, D)
		}, function($) {
			_[GnY](C, "mini-tree-loading")
		});
		this.ajaxAsync = false
	},
	ajaxAsync: false,
	YqFk: function(_, A, B, C) {
		var E = A == this.root,
			D = {
				url: this.url,
				async: this.ajaxAsync,
				type: "get",
				params: _,
				cancel: false,
				node: A,
				isRoot: E
			};
		this.fire("beforeload", D);
		if(D.cancel == true) return;
		if(A != this.root);
		var $ = this;
		this.HIU = jQuery.ajax({
			url: D.url,
			async: D.async,
			data: D.params,
			type: D.type,
			cache: false,
			dataType: "text",
			success: function(D, C, _) {
				var F = null;
				try {
					F = mini.decode(D)
				} catch(G) {
					F = []
				}
				var G = {
					result: F,
					data: F,
					cancel: false,
					node: A
				};
				if($[ATIK] == false) G.data = mini.arrayToTree(G.data, $.nodesField, $.idField, $[Rcs]);
				$.fire("preload", G);
				if(G.cancel == true) return;
				if(E) $[OUQ](G.data);
				if(B) B(G.data);
				$.fire("load", G)
			},
			error: function(_, B, A) {
				var D = {
					xmlHttp: _,
					errorCode: B
				};
				if(C) C(D);
				$.fire("loaderror", D)
			}
		})
	},
	getItemValue: function($) {
		if(!$) return "";
		var _ = $[this.idField];
		return mini.isNull(_) ? "" : String(_)
	},
	getItemText: function($) {
		if(!$) return "";
		var _ = $[this.textField];
		return mini.isNull(_) ? "" : String(_)
	},
	VgT: function($) {
		var B = this[HbI];
		if(B && this.hasChildren($)) B = this[F0q];
		var _ = this[Jach]($),
			A = {
				isLeaf: this.isLeaf($),
				node: $,
				nodeHtml: _,
				nodeCls: "",
				nodeStyle: "",
				showCheckBox: B,
				iconCls: this.getNodeIcon($),
				showTreeIcon: this.showTreeIcon
			};
		this.fire("drawnode", A);
		if(A.nodeHtml === null || A.nodeHtml === undefined || A.nodeHtml === "") A.nodeHtml = "&nbsp;";
		return A
	},
	T7$: function(D, O, H) {
		var N = !H;
		if(!H) H = [];
		var K = D[this.textField];
		if(K === null || K === undefined) K = "";
		var M = this.isLeaf(D),
			$ = this.getLevel(D),
			P = this.VgT(D),
			E = P.nodeCls;
		if(!M) E = this.isExpandedNode(D) ? this.BI : this.Bd;
		if(this.XLb == D) E += " " + this.I9W;
		var F = this[OIAh](D),
			I = F && F.length > 0;
		H[H.length] = "<div class=\"mini-tree-nodetitle " + E + "\" style=\"" + P.nodeStyle + "\">";
		var A = this[OZX](D),
			_ = 0;
		for(var J = _; J <= $; J++) {
			if(J == $) continue;
			if(M) if(this[Q9r] == false && J >= $ - 1) continue;
			var L = "";
			if(this._isInViewLastNode(D, J)) L = "background:none";
			H[H.length] = "<span class=\"mini-tree-indent \" style=\"" + L + "\"></span>"
		}
		var C = "";
		if(this._isViewFirstNode(D)) C = "mini-tree-node-ecicon-first";
		else if(this._isViewLastNode(D)) C = "mini-tree-node-ecicon-last";
		if(this._isViewFirstNode(D) && this._isViewLastNode(D)) C = "mini-tree-node-ecicon-last";
		if(!M) H[H.length] = "<a class=\"" + this.OH4l + " " + C + "\" style=\"" + (this[Q9r] ? "" : "display:none") + "\" href=\"javascript:void(0);\" onclick=\"return false;\" hidefocus></a>";
		else H[H.length] = "<span class=\"" + this.OH4l + " " + C + "\" ></span>";
		H[H.length] = "<span class=\"mini-tree-nodeshow\">";
		if(P[OvNg]) H[H.length] = "<span class=\"" + P.iconCls + " mini-tree-icon\"></span>";
		if(P[HbI]) {
			var G = this.F1y(D);
			H[H.length] = "<input type=\"checkbox\" id=\"" + G + "\" class=\"" + this.Hm + "\" hidefocus />"
		}
		H[H.length] = "<span class=\"mini-tree-nodetext\">";
		if(O) {
			var B = this.uid + "$edit$" + D._id,
				K = D[this.textField];
			if(K === null || K === undefined) K = "";
			H[H.length] = "<input id=\"" + B + "\" type=\"text\" class=\"mini-tree-editinput\" value=\"" + K + "\"/>"
		} else H[H.length] = P.nodeHtml;
		H[H.length] = "</span>";
		H[H.length] = "</span>";
		H[H.length] = "</div>";
		if(N) return H.join("")
	},
	ZV$: function(A, D) {
		var C = !D;
		if(!D) D = [];
		if(!A) return "";
		var _ = this.RLI(A),
			$ = this.isVisibleNode(A) ? "" : "display:none";
		D[D.length] = "<div id=\"";
		D[D.length] = _;
		D[D.length] = "\" class=\"";
		D[D.length] = this.XMk;
		D[D.length] = "\" style=\"";
		D[D.length] = $;
		D[D.length] = "\">";
		this.T7$(A, false, D);
		var B = this._getViewChildNodes(A);
		if(B) this.Xd(B, A, D);
		D[D.length] = "</div>";
		if(C) return D.join("")
	},
	Xd: function(F, B, G) {
		var E = !G;
		if(!G) G = [];
		if(!F) return "";
		var C = this.PIS(B),
			$ = this.isExpandedNode(B) ? "" : "display:none";
		G[G.length] = "<div id=\"";
		G[G.length] = C;
		G[G.length] = "\" class=\"";
		G[G.length] = this.GAj;
		G[G.length] = "\" style=\"";
		G[G.length] = $;
		G[G.length] = "\">";
		for(var _ = 0, D = F.length; _ < D; _++) {
			var A = F[_];
			this.ZV$(A, G)
		}
		G[G.length] = "</div>";
		if(E) return G.join("")
	},
	doUpdate: function() {
		if(!this.Y5o) return;
		var $ = this._getViewChildNodes(this.root),
			A = [];
		this.Xd($, this.root, A);
		var _ = A.join("");
		this.H23I.innerHTML = _;
		this.WU$Y()
	},
	RSm: function() {},
	WU$Y: function() {
		var $ = this;
		if(this.R3d8) return;
		this.R3d8 = setTimeout(function() {
			$[Fcv]();
			$.R3d8 = null
		}, 1)
	},
	doLayout: function() {
		if(this[HbI]) V7(this.el, "mini-tree-showCheckBox");
		else HMT(this.el, "mini-tree-showCheckBox");
		if(this[U9]) V7(this.el, "mini-tree-hottrack");
		else HMT(this.el, "mini-tree-hottrack");
		var $ = this.el.firstChild;
		if($) V7($, "mini-tree-rootnodes")
	},
	filter: function(C, B) {
		B = B || this;
		var A = this._viewNodes = {},
			_ = this.nodesField;

		function $(G) {
			var J = G[_];
			if(!J) return false;
			var K = G._id,
				H = [];
			for(var D = 0, I = J.length; D < I; D++) {
				var F = J[D],
					L = $(F),
					E = C[GkN](B, F, D, this);
				if(E === true || L) H.push(F)
			}
			if(H.length > 0) A[K] = H;
			return H.length > 0
		}
		$(this.root);
		this[Mdk]()
	},
	clearFilter: function() {
		this._viewNodes = null;
		this[Mdk]()
	},
	setShowCheckBox: function($) {
		if(this[HbI] != $) {
			this[HbI] = $;
			this[Mdk]()
		}
	},
	getShowCheckBox: function() {
		return this[HbI]
	},
	setShowFolderCheckBox: function($) {
		if(this[F0q] != $) {
			this[F0q] = $;
			this[Mdk]()
		}
	},
	getShowFolderCheckBox: function() {
		return this[F0q]
	},
	setAllowSelect: function($) {
		if(this[MHU] != $) {
			this[MHU] = $;
			this[Mdk]()
		}
	},
	getAllowSelect: function() {
		return this[MHU]
	},
	setShowTreeIcon: function($) {
		if(this[OvNg] != $) {
			this[OvNg] = $;
			this[Mdk]()
		}
	},
	getShowTreeIcon: function() {
		return this[OvNg]
	},
	setShowExpandButtons: function($) {
		if(this[Q9r] != $) {
			this[Q9r] = $;
			this[Mdk]()
		}
	},
	getShowExpandButtons: function() {
		return this[Q9r]
	},
	setEnableHotTrack: function($) {
		if(this[U9] != $) {
			this[U9] = $;
			this[Fcv]()
		}
	},
	getEnableHotTrack: function() {
		return this[U9]
	},
	setExpandOnLoad: function($) {
		if(this.expandOnLoad != $) this.expandOnLoad = $
	},
	getExpandOnLoad: function() {
		return this.expandOnLoad
	},
	setCheckRecursive: function($) {
		if(this[PiyK] != $) this[PiyK] = $
	},
	getCheckRecursive: function() {
		return this[PiyK]
	},
	getNodeIcon: function(_) {
		var $ = _[this.iconField];
		if(!$) if(this.isLeaf(_)) $ = this.leafIcon;
		else $ = this.folderIcon;
		return $
	},
	isAncestor: function(_, B) {
		if(_ == B) return true;
		if(!_ || !B) return false;
		var A = this[GC1O](B);
		for(var $ = 0, C = A.length; $ < C; $++) if(A[$] == _) return true;
		return false
	},
	getAncestors: function(A) {
		var _ = [];
		while(1) {
			var $ = this[OZX](A);
			if(!$ || $ == this.root) break;
			_[_.length] = $;
			A = $
		}
		_.reverse();
		return _
	},
	getRootNode: function() {
		return this.root
	},
	getParentNode: function($) {
		if(!$) return null;
		return this.IYa[$._pid]
	},
	_isViewFirstNode: function(_) {
		if(this._viewNodes) {
			var $ = this[OZX](_),
				A = this._getViewChildNodes($);
			return A[0] === _
		} else return this[VH1K](_)
	},
	_isViewLastNode: function(_) {
		if(this._viewNodes) {
			var $ = this[OZX](_),
				A = this._getViewChildNodes($);
			return A[A.length - 1] === _
		} else return this.isLastNode(_)
	},
	_isInViewLastNode: function(D, $) {
		if(this._viewNodes) {
			var C = null,
				A = this[GC1O](D);
			for(var _ = 0, E = A.length; _ < E; _++) {
				var B = A[_];
				if(this.getLevel(B) == $) C = B
			}
			if(!C || C == this.root) return false;
			return this._isViewLastNode(C)
		} else return this.isInLastNode(D, $)
	},
	_getViewChildNodes: function($) {
		if(this._viewNodes) return this._viewNodes[$._id];
		else return this[OIAh]($)
	},
	getChildNodes: function($) {
		$ = this[CyM]($);
		if(!$) return null;
		return $[this.nodesField]
	},
	indexOf: function(_) {
		_ = this[CyM](_);
		if(!_) return -1;
		this.getList();
		var $ = this._indexs[_[this.idField]];
		if(mini.isNull($)) return -1;
		return $
	},
	getAt: function(_) {
		var $ = this.getList();
		return $[_]
	},
	indexOfChildren: function(A) {
		var $ = this[OZX](A);
		if(!$) return -1;
		var _ = $[this.nodesField];
		return _.indexOf(A)
	},
	hasChildren: function($) {
		var _ = this[OIAh]($);
		return !!(_ && _.length > 0)
	},
	isLeaf: function($) {
		if(!$ || $.isLeaf === false) return false;
		var _ = this[OIAh]($);
		if(_ && _.length > 0) return false;
		return true
	},
	getLevel: function($) {
		return $._level
	},
	isExpandedNode: function($) {
		$ = this[CyM]($);
		if(!$) return false;
		return $.expanded == true || mini.isNull($.expanded)
	},
	isCheckedNode: function($) {
		return $.checked == true
	},
	isVisibleNode: function($) {
		return $.visible !== false
	},
	isEnabledNode: function($) {
		return $.enabled !== false || this.enabled
	},
	isFirstNode: function(_) {
		var $ = this[OZX](_),
			A = this[OIAh]($);
		return A[0] === _
	},
	isLastNode: function(_) {
		var $ = this[OZX](_),
			A = this[OIAh]($);
		return A[A.length - 1] === _
	},
	isInLastNode: function(D, $) {
		var C = null,
			A = this[GC1O](D);
		for(var _ = 0, E = A.length; _ < E; _++) {
			var B = A[_];
			if(this.getLevel(B) == $) C = B
		}
		if(!C || C == this.root) return false;
		return this.isLastNode(C)
	},
	bubbleParent: function(_, B, A) {
		A = A || this;
		if(_) B[GkN](this, _);
		var $ = this[OZX](_);
		if($ && $ != this.root) this.bubbleParent($, B, A)
	},
	cascadeChild: function(A, E, B) {
		if(!E) return;
		if(!A) A = this.root;
		var D = A[this.nodesField];
		if(D) {
			D = D.clone();
			for(var $ = 0, C = D.length; $ < C; $++) {
				var _ = D[$];
				if(E[GkN](B || this, _, $, A) === false) return;
				this.cascadeChild(_, E, B)
			}
		}
	},
	eachChild: function(B, F, C) {
		if(!F || !B) return;
		var E = B[this.nodesField];
		if(E) {
			var _ = E.clone();
			for(var A = 0, D = _.length; A < D; A++) {
				var $ = _[A];
				if(F[GkN](C || this, $, A, B) === false) break
			}
		}
	},
	QfsR: function(_, $) {
		if(!_._id) _._id = L2.NodeUID++;
		this.IYa[_._id] = _;
		this.Cn$[_[this.idField]] = _;
		_._pid = $ ? $._id : "";
		_._level = $ ? $._level + 1 : -1;
		this.cascadeChild(_, function(A, $, _) {
			if(!A._id) A._id = L2.NodeUID++;
			this.IYa[A._id] = A;
			this.Cn$[A[this.idField]] = A;
			A._pid = _._id;
			A._level = _._level + 1
		}, this);
		this._clearTree()
	},
	UQ4: function(_) {
		var $ = this;

		function A(_) {
			$.Pc22(_)
		}
		if(_ != this.root) A(_);
		this.cascadeChild(_, function($) {
			A($)
		}, this)
	},
	removeNodes: function(B) {
		if(!mini.isArray(B)) return;
		B = B.clone();
		for(var $ = 0, A = B.length; $ < A; $++) {
			var _ = B[$];
			this[BnX](_)
		}
	},
	Pc22: function($) {
		var A = this.T7$($),
			_ = this._getNodeEl($);
		if(_) jQuery(_.firstChild).replaceWith(A)
	},
	setNodeText: function(_, $) {
		_ = this[CyM](_);
		if(!_) return;
		_[this.textField] = $;
		this.Pc22(_)
	},
	setNodeIconCls: function(_, $) {
		_ = this[CyM](_);
		if(!_) return;
		_[this.iconField] = $;
		this.Pc22(_)
	},
	updateNode: function(_, $) {
		_ = this[CyM](_);
		if(!_ || !$) return;
		var A = _[this.nodesField];
		mini.copyTo(_, $);
		_[this.nodesField] = A;
		this.Pc22(_)
	},
	removeNode: function(_) {
		_ = this[CyM](_);
		if(!_) return;
		if(this.XLb == _) this.XLb = null;
		var $ = this[OZX](_);
		$[this.nodesField].remove(_);
		this.QfsR(_, $);
		var A = this._getNodeEl(_);
		if(A) A.parentNode.removeChild(A);
		this.UQ4($)
	},
	addNodes: function(C, _) {
		if(!mini.isArray(C)) return;
		for(var $ = 0, B = C.length; $ < B; $++) {
			var A = C[$];
			this.addNode(A, 10000, _)
		}
	},
	addNode: function(C, $, _) {
		C = this[CyM](C);
		if(!C) return;
		var B = _;
		switch($) {
		case "before":
			if(!B) return;
			_ = this[OZX](B);
			var A = _[this.nodesField];
			$ = A.indexOf(B);
			break;
		case "after":
			if(!B) return;
			_ = this[OZX](B);
			A = _[this.nodesField];
			$ = A.indexOf(B) + 1;
			break;
		case "add":
			break;
		default:
			break
		}
		_ = this[CyM](_);
		if(!_) _ = this.root;
		var F = _[this.nodesField];
		if(!F) F = _[this.nodesField] = [];
		$ = parseInt($);
		if(isNaN($)) $ = F.length;
		B = F[$];
		if(!B) $ = F.length;
		F.insert($, C);
		this.QfsR(C, _);
		var E = this.WbUA(_);
		if(E) {
			var H = this.ZV$(C),
				$ = F.indexOf(C) + 1,
				B = F[$];
			if(B) {
				var G = this._getNodeEl(B);
				jQuery(G).before(H)
			} else mini.append(E, H)
		} else {
			var H = this.ZV$(_),
				D = this._getNodeEl(_);
			jQuery(D).replaceWith(H)
		}
		_ = this[OZX](C);
		this.UQ4(_)
	},
	moveNodes: function(E, B, _) {
		if(!E || E.length == 0 || !B || !_) return;
		this.beginUpdate();
		var A = this;
		for(var $ = 0, D = E.length; $ < D; $++) {
			var C = E[$];
			this.moveNode(C, B, _);
			if($ != 0) {
				B = C;
				_ = "after"
			}
		}
		this.endUpdate()
	},
	moveNode: function(G, E, C) {
		G = this[CyM](G);
		E = this[CyM](E);
		if(!G || !E || !C) return false;
		if(this.isAncestor(G, E)) return false;
		var $ = -1,
			_ = null;
		switch(C) {
		case "before":
			_ = this[OZX](E);
			$ = this.indexOfChildren(E);
			break;
		case "after":
			_ = this[OZX](E);
			$ = this.indexOfChildren(E) + 1;
			break;
		default:
			_ = E;
			var B = this[OIAh](_);
			if(!B) B = _[this.nodesField] = [];
			$ = B.length;
			break
		}
		var F = {},
			B = this[OIAh](_);
		B.insert($, F);
		var D = this[OZX](G),
			A = this[OIAh](D);
		A.remove(G);
		$ = B.indexOf(F);
		B[$] = G;
		this.QfsR(G, _);
		this[Mdk]();
		return true
	},
	isEditingNode: function($) {
		return this._editingNode == $
	},
	beginEdit: function(_) {
		_ = this[CyM](_);
		if(!_) return;
		var A = this._getNodeEl(_),
			B = this.T7$(_, true),
			A = this._getNodeEl(_);
		if(A) jQuery(A.firstChild).replaceWith(B);
		this._editingNode = _;
		var $ = this.uid + "$edit$" + _._id;
		this._editInput = document.getElementById($);
		this._editInput.focus();
		mini.selectRange(this._editInput, 1000, 1000);
		$v9(this._editInput, "keydown", this.Dwq7, this);
		$v9(this._editInput, "blur", this.VVh, this)
	},
	cancelEdit: function() {
		if(this._editingNode) {
			this.Pc22(this._editingNode);
			M4(this._editInput, "keydown", this.Dwq7, this);
			M4(this._editInput, "blur", this.VVh, this)
		}
		this._editingNode = null;
		this._editInput = null
	},
	Dwq7: function(_) {
		if(_.keyCode == 13) {
			var $ = this._editInput.value;
			this.setNodeText(this._editingNode, $);
			this[Etn]()
		} else if(_.keyCode == 27) this[Etn]()
	},
	VVh: function(_) {
		var $ = this._editInput.value;
		this.setNodeText(this._editingNode, $);
		this[Etn]()
	},
	getNodeByEvent: function(C) {
		if(XPZ(C.target, this.GAj)) return null;
		var A = ZW(C.target, this.XMk);
		if(A) {
			var $ = A.id.split("$"),
				B = $[$.length - 1],
				_ = this.IYa[B];
			return _
		}
		return null
	},
	RLI: function($) {
		return this.uid + "$" + $._id
	},
	PIS: function($) {
		return this.uid + "$nodes$" + $._id
	},
	F1y: function($) {
		return this.uid + "$check$" + $._id
	},
	addNodeCls: function($, _) {
		var A = this._getNodeEl($);
		if(A) V7(A, _)
	},
	removeNodeCls: function($, _) {
		var A = this._getNodeEl($);
		if(A) HMT(A, _)
	},
	getNodeBox: function(_) {
		var $ = this._getNodeEl(_);
		if($) return Y3L($.firstChild)
	},
	_getNodeEl: function($) {
		if(!$) return null;
		var _ = this.RLI($);
		return document.getElementById(_)
	},
	_FU: function(_) {
		if(!_) return null;
		var $ = this.MFL(_);
		if($) {
			$ = mini.byClass(this.CW, $);
			return $
		}
		return null
	},
	MFL: function(_) {
		var $ = this._getNodeEl(_);
		if($) return $.firstChild
	},
	WbUA: function($) {
		if(!$) return null;
		var _ = this.PIS($);
		return document.getElementById(_)
	},
	Ho: function($) {
		if(!$) return null;
		var _ = this.F1y($);
		return document.getElementById(_)
	},
	findNodes: function(A, $) {
		var _ = [];
		$ = $ || this;
		this.cascadeChild(this.root, function(B) {
			if(A && A[GkN]($, B) === true) _.push(B)
		}, this);
		return _
	},
	getNode: function($) {
		if(typeof $ == "object") return $;
		return this.Cn$[$] || null
	},
	hideNode: function(_) {
		_ = this[CyM](_);
		if(!_) return;
		_.visible = false;
		var $ = this._getNodeEl(_);
		$.style.display = "none"
	},
	showNode: function(_) {
		_ = this[CyM](_);
		if(!_) return;
		_.visible = false;
		var $ = this._getNodeEl(_);
		$.style.display = ""
	},
	enableNode: function(A) {
		A = this[CyM](A);
		if(!A) return;
		A.enabled = true;
		var _ = this._getNodeEl(A);
		HMT(_, "mini-disabled");
		var $ = this.Ho(A);
		if($) $.disabled = false
	},
	disableNode: function(A) {
		A = this[CyM](A);
		if(!A) return;
		A.enabled = false;
		var _ = this._getNodeEl(A);
		V7(_, "mini-disabled");
		var $ = this.Ho(A);
		if($) $.disabled = true
	},
	expandNode: function(E, B) {
		E = this[CyM](E);
		if(!E) return;
		var $ = this.isExpandedNode(E);
		if($) return;
		if(this.isLeaf(E)) return;
		E.expanded = true;
		var D = this.WbUA(E);
		if(D) D.style.display = "";
		D = this._getNodeEl(E);
		if(D) {
			var G = D.firstChild;
			HMT(G, this.Bd);
			V7(G, this.BI)
		}
		this.fire("expand", {
			node: E
		});
		B = B && !(mini.isIE6);
		if(B && this._getViewChildNodes(E)) {
			this.Ms3 = true;
			D = this.WbUA(E);
			if(!D) return;
			var C = KwY(D);
			D.style.height = "1px";
			if(this.UuJM) D.style.position = "relative";
			var _ = {
				height: C + "px"
			},
				A = this,
				F = jQuery(D);
			F.animate(_, 180, function() {
				A.Ms3 = false;
				A.RSm();
				clearInterval(A.Reb);
				D.style.height = "auto";
				if(A.UuJM) D.style.position = "static"
			});
			clearInterval(this.Reb);
			this.Reb = setInterval(function() {
				A.RSm()
			}, 60)
		}
		this.RSm();
		mini[CX7](D)
	},
	collapseNode: function(E, B) {
		E = this[CyM](E);
		if(!E) return;
		var $ = this.isExpandedNode(E);
		if(!$) return;
		if(this.isLeaf(E)) return;
		E.expanded = false;
		var D = this.WbUA(E);
		if(D) D.style.display = "none";
		D = this._getNodeEl(E);
		if(D) {
			var G = D.firstChild;
			HMT(G, this.BI);
			V7(G, this.Bd)
		}
		this.fire("collapse", {
			node: E
		});
		B = B && !(mini.isIE6);
		if(B && this._getViewChildNodes(E)) {
			this.Ms3 = true;
			D = this.WbUA(E);
			if(!D) return;
			D.style.display = "";
			D.style.height = "auto";
			if(this.UuJM) D.style.position = "relative";
			var C = KwY(D),
				_ = {
					height: "1px"
				},
				A = this,
				F = jQuery(D);
			F.animate(_, 180, function() {
				D.style.display = "none";
				D.style.height = "auto";
				if(A.UuJM) D.style.position = "static";
				A.Ms3 = false;
				A.RSm();
				clearInterval(A.Reb)
			});
			clearInterval(this.Reb);
			this.Reb = setInterval(function() {
				A.RSm()
			}, 60)
		}
		this.RSm()
	},
	toggleNode: function(_, $) {
		if(this.isExpandedNode(_)) this[XI8](_, $);
		else this[LaOj](_, $)
	},
	expandLevel: function($) {
		this.cascadeChild(this.root, function(_) {
			if(this.getLevel(_) == $) if(_[this.nodesField] != null) this[LaOj](_)
		}, this)
	},
	collapseLevel: function($) {
		this.cascadeChild(this.root, function(_) {
			if(this.getLevel(_) == $) if(_[this.nodesField] != null) this[XI8](_)
		}, this)
	},
	expandAll: function() {
		this.cascadeChild(this.root, function($) {
			if($[this.nodesField] != null) this[LaOj]($)
		}, this)
	},
	collapseAll: function() {
		this.cascadeChild(this.root, function($) {
			if($[this.nodesField] != null) this[XI8]($)
		}, this)
	},
	expandPath: function(A) {
		A = this[CyM](A);
		if(!A) return;
		var _ = this[GC1O](A);
		for(var $ = 0, B = _.length; $ < B; $++) this[LaOj](_[$])
	},
	collapsePath: function(A) {
		A = this[CyM](A);
		if(!A) return;
		var _ = this[GC1O](A);
		for(var $ = 0, B = _.length; $ < B; $++) this[XI8](_[$])
	},
	selectNode: function(_) {
		_ = this[CyM](_);
		var $ = this._getNodeEl(this.XLb);
		if($) HMT($.firstChild, this.I9W);
		this.XLb = _;
		$ = this._getNodeEl(this.XLb);
		if($) V7($.firstChild, this.I9W);
		var A = {
			node: _,
			isLeaf: this.isLeaf(_)
		};
		this.fire("nodeselect", A)
	},
	getSelectedNode: function() {
		return this.XLb
	},
	getSelectedNodes: function() {
		var $ = [];
		if(this.XLb) $.push(this.XLb);
		return $
	},
	checkNode: function(_) {
		_ = this[CyM](_);
		if(!_ || _.checked) return;
		_.checked = true;
		var $ = this.Ho(_);
		if($) $.checked = true
	},
	uncheckNode: function(_) {
		_ = this[CyM](_);
		if(!_ || !_.checked) return;
		_.checked = false;
		var $ = this.Ho(_);
		if($) $.checked = false
	},
	checkNodes: function(B) {
		if(!mini.isArray(B)) B = [];
		for(var $ = 0, A = B.length; $ < A; $++) {
			var _ = B[$];
			this.checkNode(_)
		}
	},
	uncheckNodes: function(B) {
		if(!mini.isArray(B)) B = [];
		for(var $ = 0, A = B.length; $ < A; $++) {
			var _ = B[$];
			this.uncheckNode(_)
		}
	},
	checkAllNodes: function() {
		this.cascadeChild(this.root, function($) {
			this.checkNode($)
		}, this)
	},
	uncheckAllNodes: function($) {
		this.cascadeChild(this.root, function($) {
			this.uncheckNode($)
		}, this)
	},
	getCheckedNodes: function() {
		var $ = [];
		this.cascadeChild(this.root, function(_) {
			if(_.checked == true) $.push(_)
		}, this);
		return $
	},
	setValue: function(_) {
		if(mini.isNull(_)) _ = "";
		_ = String(_);
		if(this.getValue() != _) {
			var C = this.getCheckedNodes();
			this.uncheckNodes(C);
			this.value = _;
			var A = String(_).split(",");
			for(var $ = 0, B = A.length; $ < B; $++) this.checkNode(A[$])
		}
	},
	getNodesByValue: function(_) {
		if(mini.isNull(_)) _ = "";
		_ = String(_);
		var D = [],
			A = String(_).split(",");
		for(var $ = 0, C = A.length; $ < C; $++) {
			var B = this[CyM](A[$]);
			if(B) D.push(B)
		}
		return D
	},
	B5fK: function(A) {
		if(mini.isNull(A)) A = [];
		if(!mini.isArray(A)) A = this.getNodesByValue(A);
		var B = [],
			C = [];
		for(var _ = 0, D = A.length; _ < D; _++) {
			var $ = A[_];
			if($) {
				B.push(this[Ez9]($));
				C.push(this[Jach]($))
			}
		}
		return [B.join(this.delimiter), C.join(this.delimiter)]
	},
	getValue: function() {
		var A = this.getCheckedNodes(),
			C = [];
		for(var $ = 0, _ = A.length; $ < _; $++) {
			var B = this[Ez9](A[$]);
			if(B) C.push(B)
		}
		return C.join(",")
	},
	setResultAsTree: function($) {
		this[ATIK] = $
	},
	getResultAsTree: function() {
		return this[ATIK]
	},
	setParentField: function($) {
		this[Rcs] = $
	},
	getParentField: function() {
		return this[Rcs]
	},
	setIdField: function($) {
		this[OzAi] = $
	},
	getIdField: function() {
		return this[OzAi]
	},
	setTextField: function($) {
		this[LOda] = $
	},
	getTextField: function() {
		return this[LOda]
	},
	setShowTreeLines: function($) {
		this[Pi] = $;
		if($ == true) V7(this.el, "mini-tree-treeLine");
		else HMT(this.el, "mini-tree-treeLine")
	},
	getShowTreeLines: function() {
		return this[Pi]
	},
	setShowArrow: function($) {
		this.showArrow = $;
		if($ == true) V7(this.el, "mini-tree-showArrows");
		else HMT(this.el, "mini-tree-showArrows")
	},
	getShowArrow: function() {
		return this.showArrow
	},
	setIconField: function($) {
		this.iconField = $
	},
	getIconField: function() {
		return this.iconField
	},
	setNodesField: function($) {
		this.nodesField = $
	},
	getNodesField: function() {
		return this.nodesField
	},
	setTreeColumn: function($) {
		this.treeColumn = $
	},
	getTreeColumn: function() {
		return this.treeColumn
	},
	setLeafIcon: function($) {
		this.leafIcon = $
	},
	getLeafIcon: function() {
		return this.leafIcon
	},
	setFolderIcon: function($) {
		this.folderIcon = $
	},
	getFolderIcon: function() {
		return this.folderIcon
	},
	setExpandOnDblClick: function($) {
		this.expandOnDblClick = $
	},
	getExpandOnDblClick: function() {
		return this.expandOnDblClick
	},
	Esh: function(B) {
		if(!this.enabled) return;
		var $ = this.getNodeByEvent(B);
		if($) if(ZW(B.target, this.CW)) {
			var _ = this.isExpandedNode($),
				A = {
					node: $,
					expanded: _,
					cancel: false
				};
			if(this.expandOnDblClick) if(_) {
				this.fire("beforecollapse", A);
				if(A.cancel == true) return;
				this[XI8]($, this.allowAnim)
			} else {
				this.fire("beforeexpand", A);
				if(A.cancel == true) return;
				this[LaOj]($, this.allowAnim)
			}
			this.fire("nodedblclick", {
				htmlEvent: B,
				node: $
			})
		}
	},
	PGY: function(L) {
		if(!this.enabled) return;
		var B = this.getNodeByEvent(L);
		if(B) if(ZW(L.target, this.OH4l) && this.isLeaf(B) == false) {
			if(this.Ms3) return;
			var I = this.isExpandedNode(B),
				K = {
					node: B,
					expanded: I,
					cancel: false
				};
			if(I) {
				this.fire("beforecollapse", K);
				if(K.cancel == true) return;
				this[XI8](B, this.allowAnim)
			} else {
				this.fire("beforeexpand", K);
				if(K.cancel == true) return;
				this[LaOj](B, this.allowAnim)
			}
		} else if(ZW(L.target, this.Hm)) {
			var J = this.isCheckedNode(B),
				K = {
					isLeaf: this.isLeaf(B),
					node: B,
					checked: J,
					checkRecursive: this.checkRecursive,
					htmlEvent: L,
					cancel: false
				};
			this.fire("beforenodecheck", K);
			if(K.cancel == true) {
				L.preventDefault();
				return
			}
			if(J) this.uncheckNode(B);
			else this.checkNode(B);
			if(K[PiyK]) {
				this.cascadeChild(B, function($) {
					if(J) this.uncheckNode($);
					else this.checkNode($)
				}, this);
				var $ = this[GC1O](B);
				$.reverse();
				for(var G = 0, F = $.length; G < F; G++) {
					var C = $[G],
						A = this[OIAh](C),
						H = true;
					for(var _ = 0, E = A.length; _ < E; _++) {
						var D = A[_];
						if(!this.isCheckedNode(D)) {
							H = false;
							break
						}
					}
					if(H) this.checkNode(C);
					else this.uncheckNode(C)
				}
			}
			this.fire("nodecheck", K)
		} else this._OnNodeClick(B, L)
	},
	AOlf: function(_) {
		if(!this.enabled) return;
		var $ = this.getNodeByEvent(_);
		if($) if(ZW(_.target, this.OH4l));
		else if(ZW(_.target, this.Hm));
		else this._OnNodeMouseDown($, _)
	},
	_OnNodeMouseDown: function(_, $) {
		var B = ZW($.target, this.CW);
		if(!B) return null;
		if(!this.isEnabledNode(_)) return;
		var A = {
			node: _,
			cancel: false,
			isLeaf: this.isLeaf(_),
			htmlEvent: $
		};
		if(this[MHU] && _[MHU] !== false) if(this.XLb != _) {
			this.fire("beforenodeselect", A);
			if(A.cancel != true) this.selectNode(_)
		}
		this.fire("nodeMouseDown", A)
	},
	_OnNodeClick: function(_, $) {
		var B = ZW($.target, this.CW);
		if(!B) return null;
		if($.target.tagName.toLowerCase() == "a") $.target.hideFocus = true;
		if(!this.isEnabledNode(_)) return;
		var A = {
			node: _,
			cancel: false,
			isLeaf: this.isLeaf(_),
			htmlEvent: $
		};
		this.fire("nodeClick", A)
	},
	VeS: function(_) {
		var $ = this.getNodeByEvent(_);
		if($) this._OnNodeMouseMove($, _)
	},
	$MHa: function(_) {
		var $ = this.getNodeByEvent(_);
		if($) this._OnNodeMouseOut($, _)
	},
	_OnNodeMouseOut: function($, _) {
		if(!this.isEnabledNode($)) return;
		if(!ZW(_.target, this.CW)) return;
		this.blurNode();
		var _ = {
			node: $,
			htmlEvent: _
		};
		this.fire("nodemouseout", _)
	},
	_OnNodeMouseMove: function($, _) {
		if(!this.isEnabledNode($)) return;
		if(!ZW(_.target, this.CW)) return;
		if(this[U9] == true) this.focusNode($);
		var _ = {
			node: $,
			htmlEvent: _
		};
		this.fire("nodemousemove", _)
	},
	focusNode: function(A, $) {
		A = this[CyM](A);
		if(!A) return;
		var _ = this._FU(A);
		if($ && _) this[JSYh](A);
		if(this.B8bN == A) return;
		this.blurNode();
		this.B8bN = A;
		V7(_, this.Oz)
	},
	blurNode: function() {
		if(!this.B8bN) return;
		var $ = this._FU(this.B8bN);
		if($) HMT($, this.Oz);
		this.B8bN = null
	},
	scrollIntoView: function(_) {
		var $ = this._getNodeEl(_);
		mini[JSYh]($, this.el, false)
	},
	onNodeClick: function(_, $) {
		this.on("nodeClick", _, $)
	},
	onBeforeNodeSelect: function(_, $) {
		this.on("beforenodeselect", _, $)
	},
	onNodeSelect: function(_, $) {
		this.on("nodeselect", _, $)
	},
	onBeforeNodeCheck: function(_, $) {
		this.on("beforenodecheck", _, $)
	},
	onCheckNode: function(_, $) {
		this.on("nodecheck", _, $)
	},
	onNodeMouseDown: function(_, $) {
		this.on("nodemousedown", _, $)
	},
	onBeforeExpand: function(_, $) {
		this.on("beforeexpand", _, $)
	},
	onExpand: function(_, $) {
		this.on("expand", _, $)
	},
	onBeforeCollapse: function(_, $) {
		this.on("beforecollapse", _, $)
	},
	onCollapse: function(_, $) {
		this.on("collapse", _, $)
	},
	onBeforeLoad: function(_, $) {
		this.on("beforeload", _, $)
	},
	onLoad: function(_, $) {
		this.on("load", _, $)
	},
	onLoadError: function(_, $) {
		this.on("loaderror", _, $)
	},
	onDataLoad: function(_, $) {
		this.on("dataload", _, $)
	},
	IN4Data: function() {
		return this.getSelectedNodes().clone()
	},
	IN4Text: function($) {
		return "Nodes " + $.length
	},
	allowDrag: false,
	allowDrop: false,
	dragGroupName: "",
	dropGroupName: "",
	setAllowDrag: function($) {
		this.allowDrag = $
	},
	getAllowDrag: function() {
		return this.allowDrag
	},
	setAllowDrop: function($) {
		this[TCq] = $
	},
	getAllowDrop: function() {
		return this[TCq]
	},
	setDragGroupName: function($) {
		this[ABB] = $
	},
	getDragGroupName: function() {
		return this[ABB]
	},
	setDropGroupName: function($) {
		this[IlH] = $
	},
	getDropGroupName: function() {
		return this[IlH]
	},
	isAllowDrag: function($) {
		if(!this.allowDrag) return false;
		if($.allowDrag === false) return false;
		var _ = this.Jb($);
		return !_.cancel
	},
	Jb: function($) {
		var _ = {
			node: $,
			cancel: false
		};
		this.fire("DragStart", _);
		return _
	},
	MZ_E: function(_, $, A) {
		_ = _.clone();
		var B = {
			dragNodes: _,
			targetNode: $,
			action: A,
			cancel: false
		};
		this.fire("DragDrop", B);
		return B
	},
	YM8: function(A, _, $) {
		var B = {};
		B.effect = A;
		B.nodes = _;
		B.targetNode = $;
		this.fire("GiveFeedback", B);
		return B
	},
	getAttrs: function(B) {
		var F = L2[GR_][$gN][GkN](this, B);
		mini[ENl](B, F, ["value", "url", "idField", "textField", "iconField", "nodesField", "parentField", "valueField", "leafIcon", "folderIcon", "ondrawnode", "onbeforenodeselect", "onnodeselect", "onnodemousedown", "onnodeclick", "onnodedblclick", "onbeforeload", "onload", "onloaderror", "ondataload", "onbeforenodecheck", "onnodecheck", "onbeforeexpand", "onexpand", "onbeforecollapse", "oncollapse", "dragGroupName", "dropGroupName"]);
		mini[XD9s](B, F, ["allowSelect", "showCheckBox", "showExpandButtons", "showTreeIcon", "showTreeLines", "checkRecursive", "enableHotTrack", "showFolderCheckBox", "resultAsTree", "allowDrag", "allowDrop", "expandOnLoad", "showArrow", "expandOnDblClick"]);
		var D = F[OzAi] || this[OzAi],
			A = F[LOda] || this[LOda],
			E = F.iconField || this.iconField,
			_ = F.nodesField || this.nodesField;

		function $(I) {
			var N = [];
			for(var L = 0, J = I.length; L < J; L++) {
				var F = I[L],
					H = mini[OIAh](F),
					R = H[0],
					G = H[1];
				if(!R || !G) R = F;
				var C = jQuery(R),
					B = {},
					K = B[D] = R.getAttribute("value");
				B[E] = C.attr("icon");
				B[A] = R.innerHTML;
				N.add(B);
				var P = C.attr("expanded");
				if(P) B.expanded = P == "false" ? false : true;
				var Q = C.attr("allowSelect");
				if(Q) B[MHU] = Q == "false" ? false : true;
				if(!G) continue;
				var O = mini[OIAh](G),
					M = $(O);
				if(M.length > 0) B[_] = M
			}
			return N
		}
		var C = $(mini[OIAh](B));
		if(C.length > 0) F.data = C;
		if(!F[OzAi] && F[B$Gk]) F[OzAi] = F[B$Gk];
		return F
	}
});
PC7(L2, "tree");
BPF = function($) {
	this.owner = $;
	this.owner.on("NodeMouseDown", this.N0, this)
};
BPF[LMj] = {
	N0: function(B) {
		var A = B.node;
		if(B.htmlEvent.button == mini.MouseButton.Right) return;
		var _ = this.owner;
		if(_[RBE]() || _.isAllowDrag(B.node) == false) return;
		if(_.isEditingNode(A)) return;
		this.dragData = _.IN4Data();
		if(this.dragData.indexOf(A) == -1) this.dragData.push(A);
		var $ = this.IN4();
		$.start(B.htmlEvent)
	},
	Jb: function($) {
		var _ = this.owner;
		this.feedbackEl = mini.append(document.body, "<div class=\"mini-feedback\"></div>");
		this.feedbackEl.innerHTML = _.IN4Text(this.dragData);
		this.lastFeedbackClass = "";
		this[U9] = _[U9];
		_.setEnableHotTrack(false)
	},
	Pc4: function(_) {
		var A = this.owner,
			C = _.now[0],
			B = _.now[1];
		mini[Wib](this.feedbackEl, C + 15, B + 18);
		var $ = A.getNodeByEvent(_.event);
		this.dropNode = $;
		if($ && A[TCq] == true) {
			if(!A.isLeaf($) && !$[A.nodesField]) A.loadNode($);
			this.dragAction = this.getFeedback($, B, 3)
		} else this.dragAction = "no";
		this.lastFeedbackClass = "mini-feedback-" + this.dragAction;
		this.feedbackEl.className = "mini-feedback " + this.lastFeedbackClass;
		if(this.dragAction == "no") $ = null;
		this.setRowFeedback($, this.dragAction)
	},
	IzO: function(A) {
		var D = this.owner;
		mini[BnX](this.feedbackEl);
		this.feedbackEl = null;
		this.setRowFeedback(null);
		var C = [];
		for(var G = 0, F = this.dragData.length; G < F; G++) {
			var I = this.dragData[G],
				B = false;
			for(var J = 0, _ = this.dragData.length; J < _; J++) {
				var E = this.dragData[J];
				if(E != I) {
					B = D.isAncestor(E, I);
					if(B) break
				}
			}
			if(!B) C.push(I)
		}
		this.dragData = C;
		if(this.dropNode && this.dragAction != "no") {
			var K = D.MZ_E(this.dragData, this.dropNode, this.dragAction);
			if(!K.cancel) {
				var C = K.dragNodes,
					H = K.targetNode,
					$ = K.action;
				D.moveNodes(C, H, $)
			}
		}
		this.dropNode = null;
		this.dragData = null;
		D.setEnableHotTrack(this[U9])
	},
	setRowFeedback: function(B, F) {
		var A = this.owner;
		if(this.lastAddDomNode) HMT(this.lastAddDomNode, "mini-tree-feedback-add");
		if(B == null || this.dragAction == "add") {
			mini[BnX](this.feedbackLine);
			this.feedbackLine = null
		}
		this.lastRowFeedback = B;
		if(B != null) if(F == "before" || F == "after") {
			if(!this.feedbackLine) this.feedbackLine = mini.append(document.body, "<div class='mini-feedback-line'></div>");
			this.feedbackLine.style.display = "block";
			var D = A.getNodeBox(B),
				E = D.x,
				C = D.y - 1;
			if(F == "after") C += D.height;
			mini[Wib](this.feedbackLine, E, C);
			var _ = A.getBox(true);
			Sbkj(this.feedbackLine, _.width)
		} else {
			var $ = A.MFL(B);
			V7($, "mini-tree-feedback-add");
			this.lastAddDomNode = $
		}
	},
	getFeedback: function($, I, F) {
		var A = this.owner,
			J = A.getNodeBox($),
			_ = J.height,
			H = I - J.y,
			G = null;
		if(this.dragData.indexOf($) != -1) return "no";
		var C = false;
		if(F == 3) {
			C = A.isLeaf($);
			for(var E = 0, D = this.dragData.length; E < D; E++) {
				var K = this.dragData[E],
					B = A.isAncestor(K, $);
				if(B) {
					G = "no";
					break
				}
			}
		}
		if(G == null) if(C) {
			if(H > _ / 2) G = "after";
			else G = "before"
		} else if(H > (_ / 3) * 2) G = "after";
		else if(_ / 3 <= H && H <= (_ / 3 * 2)) G = "add";
		else G = "before";
		var L = A.YM8(G, this.dragData, $);
		return L.effect
	},
	IN4: function() {
		if(!this.drag) this.drag = new mini.Drag({
			capture: false,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this.drag
	}
};
_kJP = function() {
	this.data = [];
	this.Cy = {};
	this.YNn = [];
	this.VNi = {};
	this.columns = [];
	this.QA9 = [];
	this.HvFJ = {};
	this.YE = {};
	this.MyQ = [];
	this.Sj$W = {};
	_kJP[GR_][KNT][GkN](this);
	this[Mdk]();
	var $ = this;
	setTimeout(function() {
		if($.autoLoad) $.reload()
	}, 1);
	this.LDts.style.display = this[JkX] ? "" : "none"
};
Jcq = 0;
WK = 0;
Iov(_kJP, FIa, {
	C4U: "block",
	width: 300,
	height: "auto",
	minWidth: 300,
	minHeight: 150,
	maxWidth: 5000,
	maxHeight: 3000,
	allowCellWrap: false,
	bodyCls: "",
	bodyStyle: "",
	footerCls: "",
	footerStyle: "",
	pagerCls: "",
	pagerStyle: "",
	idField: "id",
	data: [],
	columns: null,
	allowResize: false,
	selectOnLoad: false,
	_rowIdField: "_uid",
	columnWidth: 120,
	columnMinWidth: 20,
	columnMaxWidth: 2000,
	fitColumns: true,
	autoHideRowDetail: true,
	showHeader: true,
	showFooter: true,
	showTop: false,
	showHGridLines: true,
	showVGridLines: true,
	showFilterRow: false,
	showSummaryRow: false,
	allowSortColumn: true,
	allowMoveColumn: true,
	allowResizeColumn: true,
	enableHotTrack: true,
	allowRowSelect: true,
	multiSelect: false,
	allowAlternating: false,
	FT4: "mini-grid-row-alt",
	L6i: "mini-grid-frozen",
	SRSo: "mini-grid-frozenCell",
	frozenStartColumn: -1,
	frozenEndColumn: -1,
	isFrozen: function() {
		return this[LdGz] >= 0 && this[T94] >= this[LdGz]
	},
	Sz4J: "mini-grid-row",
	FML: "mini-grid-row-hover",
	S9v: "mini-grid-row-selected",
	uiCls: "mini-datagrid",
	_create: function() {
		var $ = this.el = document.createElement("div");
		this.el.className = "mini-grid";
		this.el.style.display = "block";
		var _ = "<div class=\"mini-grid-border\">" + "<div class=\"mini-grid-header\"></div>" + "<div class=\"mini-grid-filterRow\"></div>" + "<div class=\"mini-grid-body\"></div>" + "<div class=\"mini-grid-scroller\"><div></div></div>" + "<div class=\"mini-grid-summaryRow\"></div>" + "<div class=\"mini-grid-footer\"></div>" + "<div class=\"mini-grid-resizeGrid\" style=\"\"></div>" + "<a href=\"#\" class=\"mini-grid-focus\" style=\"position:absolute;left:-10px;top:-10px;width:0px;height:0px;outline:none\" hideFocus onclick=\"return false\"></a>" + "</div>";
		this.el.innerHTML = _;
		this.TG = this.el.firstChild;
		this.NL = this.TG.childNodes[0];
		this.Gnmr = this.TG.childNodes[1];
		this.H23I = this.TG.childNodes[2];
		this.DYd = this.TG.childNodes[3];
		this.NsTj = this.TG.childNodes[4];
		this.Yj = this.TG.childNodes[5];
		this.LDts = this.TG.childNodes[6];
		this._focusEl = this.TG.childNodes[7];
		this.Scmd();
		this.JDF();
		Gn(this.H23I, this.bodyStyle);
		V7(this.H23I, this.bodyCls);
		this.WOUR();
		this.VNHkRows()
	},
	VNHkRows: function() {
		this.Yj.style.display = this[B8] ? "" : "none";
		this.NsTj.style.display = this[AMb] ? "" : "none";
		this.Gnmr.style.display = this[PLQ] ? "" : "none";
		this.NL.style.display = this.showHeader ? "" : "none"
	},
	focus: function() {
		try {
			this._focusEl.focus()
		} catch($) {}
	},
	WOUR: function() {
		this.pager = new I1B();
		this.pager[Ivp](this.Yj);
		this.bindPager(this.pager)
	},
	setPager: function($) {
		if(typeof $ == "string") {
			var _ = Hcd($);
			if(!_) return;
			mini.parse($);
			$ = mini.get($)
		}
		if($) this.bindPager($)
	},
	bindPager: function($) {
		$.on("pagechanged", this.LFz, this);
		this.on("load", function(_) {
			$.update(this.pageIndex, this.pageSize, this[Ak]);
			this.totalPage = $.totalPage
		}, this)
	},
	destroy: function($) {
		if(this.H23I) {
			mini[J9](this.H23I);
			this.H23I = null
		}
		if(this.DYd) {
			mini[J9](this.DYd);
			this.DYd = null
		}
		this.TG = null;
		this.NL = null;
		this.Gnmr = null;
		this.H23I = null;
		this.DYd = null;
		this.NsTj = null;
		this.Yj = null;
		this.LDts = null;
		_kJP[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		IZY2(function() {
			$v9(this.el, "click", this.PGY, this);
			$v9(this.el, "dblclick", this.Esh, this);
			$v9(this.el, "mousedown", this.AOlf, this);
			$v9(this.el, "mouseup", this.Hfq, this);
			$v9(this.el, "mousemove", this.VeS, this);
			$v9(this.el, "mouseover", this.LZR, this);
			$v9(this.el, "mouseout", this.$MHa, this);
			$v9(this.el, "keydown", this.HtB, this);
			$v9(this.el, "keyup", this.O83, this);
			$v9(this.el, "contextmenu", this.M35, this);
			$v9(this.H23I, "scroll", this.PM, this);
			$v9(this.DYd, "scroll", this.AdE, this);
			$v9(this.el, "mousewheel", this.W8, this)
		}, this);
		this.Dq = new Ahk(this);
		this.OWh8 = new TsR5(this);
		this.$jl = new JVK(this);
		this.PD = new WvN(this);
		this._CellTip = new mini._GridCellToolTip(this);
		this._Sort = new mini._GridSort(this)
	},
	setIdField: function($) {
		this[OzAi] = $
	},
	getIdField: function() {
		return this[OzAi]
	},
	setUrl: function($) {
		this.url = $
	},
	getUrl: function($) {
		return this.url
	},
	setAutoLoad: function($) {
		this.autoLoad = $
	},
	getAutoLoad: function($) {
		return this.autoLoad
	},
	PmeX: true,
	loadData: function(A) {
		if(!mini.isArray(A)) A = [];
		this.data = A;
		if(this.PmeX == true) this.VNi = {};
		this.YNn = [];
		this.Cy = {};
		this.MyQ = [];
		this.Sj$W = {};
		for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$];
			_._uid = Jcq++;
			_._index = $;
			this.Cy[_._uid] = _
		}
		this[Mdk]()
	},
	setData: function($) {
		this[M3b]($)
	},
	getData: function() {
		return this.data.clone()
	},
	toArray: function() {
		return this.data.clone()
	},
	getRange: function(A, C) {
		if(A > C) {
			var D = A;
			A = C;
			C = D
		}
		var B = this.data,
			E = [];
		for(var _ = A, F = C; _ <= F; _++) {
			var $ = B[_];
			E.push($)
		}
		return E
	},
	selectRange: function($, _) {
		if(!mini.isNumber($)) $ = this.indexOf($);
		if(!mini.isNumber(_)) _ = this.indexOf(_);
		if(mini.isNull($) || mini.isNull(_)) return;
		var A = this.getRange($, _);
		this[SsB](A)
	},
	getHeaderHeight: function() {
		return this.showHeader ? KwY(this.NL) : 0
	},
	getFooterHeight: function() {
		return this[B8] ? KwY(this.Yj) : 0
	},
	getFilterRowHeight: function() {
		return this[PLQ] ? KwY(this.Gnmr) : 0
	},
	getSummaryRowHeight: function() {
		return this[AMb] ? KwY(this.NsTj) : 0
	},
	FtbS: function() {
		return this[$hR]() ? KwY(this.DYd) : 0
	},
	LLWI: function(D) {
		var F = "",
			B = this[Mh5i]();
		if(isIE) {
			if(isIE6 || isIE7 || (isIE8 && !jQuery.boxModel) || (isIE9 && !jQuery.boxModel)) F += "<tr style=\"display:none;\">";
			else F += "<tr >"
		} else F += "<tr style=\"height:0px\">";
		for(var $ = 0, C = B.length; $ < C; $++) {
			var A = B[$],
				_ = A.width,
				E = this.Iu0b(A) + "$" + D;
			F += "<td id=\"" + E + "\" style=\"padding:0;border:0;margin:0;height:0px;";
			if(A.width) F += "width:" + A.width;
			if($ < this[LdGz] || A.visible == false) F += ";display:none;";
			F += "\" ></td>"
		}
		F += "</tr>";
		return F
	},
	Scmd: function() {
		if(this.Gnmr.firstChild) this.Gnmr.removeChild(this.Gnmr.firstChild);
		var B = this[$hR](),
			C = this[Mh5i](),
			F = [];
		F[F.length] = "<table class=\"mini-grid-table\" cellspacing=\"0\" cellpadding=\"0\">";
		F[F.length] = this.LLWI("filter");
		F[F.length] = "<tr >";
		for(var $ = 0, D = C.length; $ < D; $++) {
			var A = C[$],
				E = this.UW0(A);
			F[F.length] = "<td id=\"";
			F[F.length] = E;
			F[F.length] = "\" class=\"mini-grid-filterCell\" style=\"";
			if((B && $ < this[LdGz]) || A.visible == false || A._hide == true) F[F.length] = ";display:none;";
			F[F.length] = "\"><span class=\"mini-grid-hspace\"></span></td>"
		}
		F[F.length] = "</tr></table>";
		this.Gnmr.innerHTML = F.join("");
		for($ = 0, D = C.length; $ < D; $++) {
			A = C[$];
			if(A.filter) {
				var _ = this.getFilterCellEl($);
				A.filter[Ivp](_)
			}
		}
	},
	JDF: function() {
		if(this.NsTj.firstChild) this.NsTj.removeChild(this.NsTj.firstChild);
		var A = this[$hR](),
			B = this[Mh5i](),
			E = [];
		E[E.length] = "<table class=\"mini-grid-table\" cellspacing=\"0\" cellpadding=\"0\">";
		E[E.length] = this.LLWI("summary");
		E[E.length] = "<tr >";
		for(var $ = 0, C = B.length; $ < C; $++) {
			var _ = B[$],
				D = this.P7T(_);
			E[E.length] = "<td id=\"";
			E[E.length] = D;
			E[E.length] = "\" class=\"mini-grid-summaryCell\" style=\"";
			if((A && $ < this[LdGz]) || _.visible == false || _._hide == true) E[E.length] = ";display:none;";
			E[E.length] = "\"><span class=\"mini-grid-hspace\"></span></td>"
		}
		E[E.length] = "</tr></table>";
		this.NsTj.innerHTML = E.join("")
	},
	Fy: function(L) {
		L = L || "";
		var N = this[$hR](),
			A = this.Y7(),
			G = this[Mh5i](),
			H = G.length,
			F = [];
		F[F.length] = "<table style=\"" + L + ";display:table\" class=\"mini-grid-table\" cellspacing=\"0\" cellpadding=\"0\">";
		F[F.length] = this.LLWI("header");
		for(var M = 0, _ = A.length; M < _; M++) {
			var D = A[M];
			F[F.length] = "<tr >";
			for(var I = 0, E = D.length; I < E; I++) {
				var B = D[I],
					C = B.header;
				if(typeof C == "function") C = C[GkN](this, B);
				if(mini.isNull(C) || C === "") C = "&nbsp;";
				var J = this.Iu0b(B),
					$ = "";
				if(this.sortField == B.field) $ = this.sortOrder == "asc" ? "mini-grid-asc" : "mini-grid-desc";
				F[F.length] = "<td id=\"";
				F[F.length] = J;
				F[F.length] = "\" class=\"mini-grid-headerCell " + $ + " " + (B.headerCls || "") + " ";
				if(I == H - 1) F[F.length] = " mini-grid-last-column ";
				F[F.length] = "\" style=\"";
				var K = G.indexOf(B);
				if((N && K != -1 && K < this[LdGz]) || B.visible == false || B._hide == true) F[F.length] = ";display:none;";
				if(B.columns && B.columns.length > 0 && B.colspan == 0) F[F.length] = ";display:none;";
				if(B.headerStyle) F[F.length] = B.headerStyle + ";";
				if(B.headerAlign) F[F.length] = "text-align:" + B.headerAlign + ";";
				F[F.length] = "\" ";
				if(B.rowspan) F[F.length] = "rowspan=\"" + B.rowspan + "\" ";
				if(B.colspan) F[F.length] = "colspan=\"" + B.colspan + "\" ";
				F[F.length] = "><div class=\"mini-grid-cellInner\">";
				F[F.length] = C;
				if($) F[F.length] = "<span class=\"mini-grid-sortIcon\"></span>";
				F[F.length] = "</div>";
				F[F.length] = "</td>"
			}
			F[F.length] = "</tr>"
		}
		F[F.length] = "</table>";
		var O = F.join("");
		O = "<div class=\"mini-grid-header\">" + O + "</div>";
		this.NL.innerHTML = F.join("");
		this.fire("refreshHeader")
	},
	Ged: Ged = function() {

	},
	CnT: Ged(),
	SnP: function(E, C, O) {
		if(!mini.isNumber(O)) O = this.data.indexOf(E);
		var K = O == this.data.length - 1,
			M = this[$hR](),
			N = !C;
		if(!C) C = [];
		var A = this[Mh5i](),
			F = -1,
			H = " ",
			D = -1,
			I = " ";
		C[C.length] = "<tr id=\"";
		C[C.length] = this.AsR(E);
		C[C.length] = "\" class=\"mini-grid-row ";
		if(this[Jm](E)) {
			C[C.length] = this.S9v;
			C[C.length] = " "
		}
		if(E._state == "deleted") C[C.length] = "mini-grid-deleteRow ";
		if(E._state == "added") C[C.length] = "mini-grid-newRow ";
		if(this[PSk] && O % 2 == 1) {
			C[C.length] = this.FT4;
			C[C.length] = " "
		}
		F = C.length;
		C[C.length] = H;
		C[C.length] = "\" style=\"";
		D = C.length;
		C[C.length] = I;
		C[C.length] = "\">";
		var G = A.length - 1;
		for(var J = 0, $ = G; J <= $; J++) {
			var _ = A[J],
				L = _.field ? this.FiTN(E, _.field) : false,
				P = this.GgLg(E, _, O, J),
				B = this.SjTu(E, _);
			C[C.length] = "<td id=\"";
			C[C.length] = B;
			C[C.length] = "\" class=\"mini-grid-cell ";
			if(P.cellCls) C[C.length] = P.cellCls;
			if(this.Zg_Z && this.Zg_Z[0] == E && this.Zg_Z[1] == _) {
				C[C.length] = " ";
				C[C.length] = this.CGq
			}
			if(K) C[C.length] = " mini-grid-last-row ";
			if(J == G) C[C.length] = " mini-grid-last-column ";
			if(M && this[LdGz] <= J && J <= this[T94]) {
				C[C.length] = " ";
				C[C.length] = this.SRSo + " "
			}
			C[C.length] = "\" style=\"";
			if(_.align) {
				C[C.length] = "text-align:";
				C[C.length] = _.align;
				C[C.length] = ";"
			}
			if(P.allowCellWrap) C[C.length] = "white-space:normal;text-overflow:normal;word-break:normal;";
			if(P.cellStyle) {
				C[C.length] = P.cellStyle;
				C[C.length] = ";"
			}
			if(M && J < this[LdGz] || _.visible == false) C[C.length] = "display:none;";
			C[C.length] = "\">";
			if(L) C[C.length] = "<div class=\"mini-grid-cell-dirty\">";
			C[C.length] = P.cellHtml;
			if(L) C[C.length] = "</div>";
			C[C.length] = "</td>";
			if(P.rowCls) H = P.rowCls;
			if(P.rowStyle) I = P.rowStyle
		}
		C[F] = H;
		C[D] = I;
		C[C.length] = "</tr>";
		if(N) return C.join("")
	},
	getScrollLeft: function() {
		return this[$hR]() ? this.DYd.scrollLeft : this.H23I.scrollLeft
	},
	doUpdate: function() {
		var K = new Date();
		if(this.Y5o === false) return;
		if(this[_H]() == true) this[QlR]("mini-grid-auto");
		else this[NeI]("mini-grid-auto");
		var C = this[Mh5i]();
		for(var G = 0, D = C.length; G < D; G++) {
			var B = C[G];
			delete B._hide
		}
		this.Fy();
		var J = this.data,
			F = [],
			L = this[_H]();
		if(L) F[F.length] = "<table class=\"mini-grid-table\" cellspacing=\"0\" cellpadding=\"0\">";
		else F[F.length] = "<table style=\"position:absolute;top:0;left:0;\" class=\"mini-grid-table\" cellspacing=\"0\" cellpadding=\"0\">";
		F[F.length] = this.LLWI("body");
		if(this[BX1N]()) {
			var H = this.H_();
			for(var M = 0, $ = H.length; M < $; M++) {
				var _ = H[M],
					E = _.id,
					N = this.OlT(_);
				F[F.length] = "<tr id=\"" + E + "\" class=\"mini-grid-groupRow\"><td class=\"mini-grid-groupCell\" colspan=\"" + C.length + "\"><div class=\"mini-grid-groupHeader\">";
				F[F.length] = "<div class=\"mini-grid-group-ecicon\"></div>";
				F[F.length] = "<div class=\"mini-grid-groupTitle\">" + N.cellHtml + "</div>";
				F[F.length] = "</div></td></tr>";
				var A = _.rows;
				for(G = 0, D = A.length; G < D; G++) {
					var I = A[G];
					this.SnP(I, F, G)
				}
				if(this.showGroupSummary);
			}
		} else for(G = 0, D = J.length; G < D; G++) {
			I = J[G];
			this.SnP(I, F, G)
		}
		F[F.length] = "</table>";
		if(this.H23I.firstChild) this.H23I.removeChild(this.H23I.firstChild);
		this.H23I.innerHTML = F.join("");
		if(this[$hR]()) this.AdE();
		this[Fcv]()
	},
	QyZn: function() {
		if(isIE) {
			this.TG.style.display = "none";
			h = this[DTTy](true);
			w = this[Y1Q](true);
			this.TG.style.display = ""
		}
	},
	WU$Y: function() {
		this[Fcv]()
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		var J = new Date(),
			L = this[$hR](),
			I = this.NL.firstChild,
			C = this.H23I.firstChild,
			F = this.Gnmr.firstChild,
			$ = this.NsTj.firstChild,
			K = this[_H]();
		h = this[DTTy](true);
		B = this[Y1Q](true);
		var H = B;
		if(H < 0) H = 0;
		if(h < 0) h = 0;
		var G = H,
			_ = 2000;
		if(!K) {
			h = h - this[Ykh2]() - this[XZ5f]() - this[AOJr]() - this.getSummaryRowHeight() - this.FtbS();
			if(h < 0) h = 0;
			this.H23I.style.height = h + "px";
			_ = h
		} else this.H23I.style.height = "auto";
		var D = this.H23I.scrollHeight,
			A = jQuery(this.H23I).css("overflow-y") == "hidden";
		if(this.fitColumns) {
			if(A || _ >= D) {
				var B = G + "px";
				I.style.width = B;
				C.style.width = B;
				F.style.width = B;
				$.style.width = B
			} else {
				B = parseInt(G - 17) + "px";
				I.style.width = B;
				C.style.width = B;
				F.style.width = B;
				$.style.width = B
			}
			if(K) if(G >= this.H23I.scrollWidth) this.H23I.style.height = "auto";
			else this.H23I.style.height = (C.offsetHeight + 17) + "px";
			if(K && L) this.H23I.style.height = "auto"
		} else {
			I.style.width = C.style.width = "0px";
			F.style.width = $.style.width = "0px"
		}
		if(this.fitColumns) {
			if(!A && _ < D) {
				this.NL.style.width = (H - 17) + "px";
				this.Gnmr.style.width = (H - 17) + "px";
				this.NsTj.style.width = (H - 17) + "px";
				this.Yj.style.width = (H - 17) + "px"
			} else {
				this.NL.style.width = "100%";
				this.Gnmr.style.width = "100%";
				this.NsTj.style.width = "100%";
				this.Yj.style.width = "auto"
			}
		} else {
			this.NL.style.width = "100%";
			this.Gnmr.style.width = "100%";
			this.NsTj.style.width = "100%";
			this.Yj.style.width = "auto"
		}
		if(this[$hR]()) {
			if(!A && this.H23I.offsetHeight < this.H23I.scrollHeight) this.DYd.style.width = (H - 17) + "px";
			else this.DYd.style.width = (H) + "px";
			if(this.H23I.offsetWidth < C.offsetWidth) {
				this.DYd.firstChild.style.width = this.W0H() + "px";
				I.style.width = C.style.width = "0px";
				F.style.width = $.style.width = "0px"
			} else this.DYd.firstChild.style.width = "0px"
		}
		var E = this;
		if(!this._innerLayoutTimer) this._innerLayoutTimer = setTimeout(function() {
			E._doInnerLayout();
			E._innerLayoutTimer = null
		}, 10);
		this.fire("layout")
	},
	_doInnerLayout: function() {
		this.X5At();
		this.K6();
		mini.layout(this.Gnmr);
		mini.layout(this.NsTj);
		mini.layout(this.Yj);
		mini[CX7](this.el);
		this._doLayouted = true
	},
	setFitColumns: function($) {
		this.fitColumns = $;
		if(this.fitColumns) HMT(this.el, "mini-grid-fixcolumns");
		else V7(this.el, "mini-grid-fixcolumns");
		this[Fcv]()
	},
	getFitColumns: function($) {
		return this.fitColumns
	},
	W0H: function() {
		if(this.H23I.offsetWidth < this.H23I.firstChild.offsetWidth) {
			var _ = 0,
				B = this[Mh5i]();
			for(var $ = 0, C = B.length; $ < C; $++) {
				var A = B[$];
				_ += this[B0rI](A)
			}
			return _
		} else return 0
	},
	AsR: function($) {
		return this.uid + "$" + $._uid
	},
	Iu0b: function($) {
		return this.uid + "$column$" + $._id
	},
	SjTu: function($, _) {
		return this.uid + "$" + $._uid + "$" + _._id
	},
	UW0: function($) {
		return this.uid + "$filter$" + $._id
	},
	P7T: function($) {
		return this.uid + "$summary$" + $._id
	},
	C85: function($) {
		return this.uid + "$detail$" + $._uid
	},
	getFilterCellEl: function($) {
		$ = this[ZUT$]($);
		if(!$) return null;
		return document.getElementById(this.UW0($))
	},
	getSummaryCellEl: function($) {
		$ = this[ZUT$]($);
		if(!$) return null;
		return document.getElementById(this.P7T($))
	},
	_jX: function($, _) {
		$ = this[Ykd]($);
		_ = this[ZUT$](_);
		if(!$ || !_) return null;
		var A = this.SjTu($, _);
		return document.getElementById(A)
	},
	Y2B: function($) {
		$ = this[Ykd]($);
		if(!$) return null;
		return document.getElementById(this.AsR($))
	},
	getCellBox: function(_, A) {
		_ = this[Ykd](_);
		A = this[ZUT$](A);
		if(!_ || !A) return null;
		var $ = this._jX(_, A);
		if(!$) return null;
		return Y3L($)
	},
	getRowBox: function(_) {
		var $ = this.Y2B(_);
		if($) return Y3L($);
		return null
	},
	getRowsBox: function() {
		var G = [],
			C = this.data,
			B = 0;
		for(var _ = 0, E = C.length; _ < E; _++) {
			var A = C[_],
				F = this.AsR(A),
				$ = document.getElementById(F);
			if($) {
				var D = $.offsetHeight;
				G[_] = {
					top: B,
					height: D,
					bottom: B + D
				};
				B += D
			}
		}
		return G
	},
	setColumns: function(value) {
		if(!mini.isArray(value)) value = [];
		this.columns = value;
		this.HvFJ = {};
		this.YE = {};
		this.QA9 = [];
		this.maxColumnLevel = 0;
		var level = 0;

		function init(column, index, parentColumn) {
			if(column.type) {
				if(!mini.isNull(column.header) && typeof column.header !== "function") if(column.header.trim() == "") delete column.header;
				var col = mini[S56](column.type);
				if(col) {
					var _column = mini.copyTo({}, column);
					mini.copyTo(column, col);
					mini.copyTo(column, _column)
				}
			}
			if(typeof column.init == "function") {
				column.init(this);
				delete column.init
			}
			var width = parseInt(column.width);
			if(mini.isNumber(width) && String(width) == column.width) column.width = width + "px";
			if(mini.isNull(column.width)) column.width = this[DzN] + "px";
			column.visible = column.visible !== false;
			column[JkX] = column.allowRresize !== false;
			column.allowMove = column.allowMove !== false;
			column.allowSort = column.allowSort === true;
			column.allowDrag = !! column.allowDrag;
			column[Egr] = !! column[Egr];
			column._id = WK++;
			column._gridUID = this.uid;
			column[Vl4] = this[Vl4];
			column._pid = parentColumn == this ? -1 : parentColumn._id;
			this.HvFJ[column._id] = column;
			if(column.name) this.YE[column.name] = column;
			if(!column.columns || column.columns.length == 0) this.QA9.push(column);
			column.level = level;
			level += 1;
			this[I2J](column, init, this);
			level -= 1;
			if(column.level > this.maxColumnLevel) this.maxColumnLevel = column.level;
			if(typeof column.filter == "string") column.filter = eval("(" + column.filter + ")");
			if(column.filter && !column.filter.el) column.filter = mini.create(column.filter);
			if(typeof column.init == "function" && column.inited != true) column.init(this);
			column.inited = true
		}
		this[I2J](this, init, this);
		this.Scmd();
		this.JDF();
		this[Mdk]()
	},
	getColumns: function() {
		return this.columns
	},
	getBottomColumns: function() {
		return this.QA9
	},
	getBottomVisibleColumns: function() {
		var A = [];
		for(var $ = 0, B = this.QA9.length; $ < B; $++) {
			var _ = this.QA9[$];
			if(this[H$](_)) A.push(_)
		}
		return A
	},
	eachColumns: function(B, F, C) {
		var D = B.columns;
		if(D) {
			var _ = D.clone();
			for(var A = 0, E = _.length; A < E; A++) {
				var $ = _[A];
				if(F[GkN](C, $, A, B) === false) break
			}
		}
	},
	getColumn: function($) {
		var _ = typeof $;
		if(_ == "number") return this[Mh5i]()[$];
		else if(_ == "object") return $;
		else return this.YE[$]
	},
	YD: function($) {
		return this.HvFJ[$]
	},
	getParentColumn: function($) {
		$ = this[ZUT$]($);
		var _ = $._pid;
		if(_ == -1) return this;
		return this.HvFJ[_]
	},
	getAncestorColumns: function(A) {
		var _ = [];
		while(1) {
			var $ = this[U1O](A);
			if(!$ || $ == this) break;
			_[_.length] = $;
			A = $
		}
		_.reverse();
		return _
	},
	isAncestorColumn: function(_, B) {
		if(_ == B) return true;
		if(!_ || !B) return false;
		var A = this[Qq1](B);
		for(var $ = 0, C = A.length; $ < C; $++) if(A[$] == _) return true;
		return false
	},
	isVisibleColumn: function(_) {
		_ = this[ZUT$](_);
		var A = this[Qq1](_);
		for(var $ = 0, B = A.length; $ < B; $++) if(A[$].visible == false) return false;
		return true
	},
	removeColumn: function($) {
		$ = this[ZUT$]($);
		var _ = this[U1O]($);
		if($ && _) {
			_.columns.remove($);
			this[Z9i](this.columns)
		}
		return $
	},
	moveColumn: function(C, _, A) {
		C = this[ZUT$](C);
		_ = this[ZUT$](_);
		if(!C || !_ || !A || C == _) return;
		if(this[Et](C, _)) return;
		var D = this[U1O](C);
		if(D) D.columns.remove(C);
		var B = _,
			$ = A;
		if($ == "before") {
			B = this[U1O](_);
			$ = B.columns.indexOf(_)
		} else if($ == "after") {
			B = this[U1O](_);
			$ = B.columns.indexOf(_) + 1
		} else if($ == "add" || $ == "append") {
			if(!B.columns) B.columns = [];
			$ = B.columns.length
		} else if(!mini.isNumber($)) return;
		B.columns.insert($, C);
		this[Z9i](this.columns)
	},
	hideColumn: function($) {
		$ = this[ZUT$]($);
		if(!$) return;
		$.visible = false;
		this.Ulc($, false);
		this.Fy();
		this[Fcv]();
		this.QyZn()
	},
	showColumn: function($) {
		$ = this[ZUT$]($);
		if(!$) return;
		$.visible = true;
		this.Ulc($, true);
		this.Fy();
		this[Fcv]();
		this.QyZn()
	},
	setColumnWidth: function(E, B) {
		E = this[ZUT$](E);
		if(!E) return;
		if(mini.isNumber(B)) B += "px";
		E.width = B;
		var _ = this.Iu0b(E) + "$header",
			F = this.Iu0b(E) + "$body",
			A = this.Iu0b(E) + "$filter",
			D = this.Iu0b(E) + "$summary",
			C = document.getElementById(_),
			$ = document.getElementById(F),
			G = document.getElementById(A),
			H = document.getElementById(D);
		if(C) C.style.width = B;
		if($) $.style.width = B;
		if(G) G.style.width = B;
		if(H) H.style.width = B;
		this[Fcv]()
	},
	getColumnWidth: function(B) {
		B = this[ZUT$](B);
		if(!B) return 0;
		if(B.visible == false) return 0;
		var _ = 0,
			C = this.Iu0b(B) + "$body",
			A = document.getElementById(C);
		if(A) {
			var $ = A.style.display;
			A.style.display = "";
			_ = R0(A);
			A.style.display = $
		}
		return _
	},
	Y7: function() {
		var _ = this[Lq1n](),
			D = [];
		for(var C = 0, F = _; C <= F; C++) D.push([]);

		function A(C) {
			var D = mini[S6d](C.columns, "columns"),
				A = 0;
			for(var $ = 0, B = D.length; $ < B; $++) {
				var _ = D[$];
				if(_.visible != true || _._hide == true) continue;
				if(!_.columns || _.columns.length == 0) A += 1
			}
			return A
		}
		var $ = mini[S6d](this.columns, "columns");
		for(C = 0, F = $.length; C < F; C++) {
			var E = $[C],
				B = D[E.level];
			if(E.columns && E.columns.length > 0) E.colspan = A(E);
			if((!E.columns || E.columns.length == 0) && E.level < _) E.rowspan = _ - E.level + 1;
			B.push(E)
		}
		return D
	},
	getMaxColumnLevel: function() {
		return this.maxColumnLevel
	},
	Ulc: function(C, N) {
		var I = document.getElementById(this.Iu0b(C));
		if(I) I.style.display = N ? "" : "none";
		var D = document.getElementById(this.UW0(C));
		if(D) D.style.display = N ? "" : "none";
		var _ = document.getElementById(this.P7T(C));
		if(_) _.style.display = N ? "" : "none";
		var J = this.Iu0b(C) + "$header",
			M = this.Iu0b(C) + "$body",
			B = this.Iu0b(C) + "$filter",
			E = this.Iu0b(C) + "$summary",
			L = document.getElementById(J);
		if(L) L.style.display = N ? "" : "none";
		var O = document.getElementById(B);
		if(O) O.style.display = N ? "" : "none";
		var P = document.getElementById(E);
		if(P) P.style.display = N ? "" : "none";
		if($) {
			if(N && $.style.display == "") return;
			if(!N && $.style.display == "none") return
		}
		var $ = document.getElementById(M);
		if($) $.style.display = N ? "" : "none";
		for(var H = 0, F = this.data.length; H < F; H++) {
			var K = this.data[H],
				G = this.SjTu(K, C),
				A = document.getElementById(G);
			if(A) A.style.display = N ? "" : "none"
		}
	},
	BNL: function(C, D, B) {
		for(var $ = 0, E = this.data.length; $ < E; $++) {
			var A = this.data[$],
				F = this.SjTu(A, C),
				_ = document.getElementById(F);
			if(_) if(B) V7(_, D);
			else HMT(_, D)
		}
	},
	AN94: function() {
		if(!this[$hR]()) return;
		var A = this[Mh5i]();
		for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$];
			if(this[LdGz] <= $ && $ <= this[T94]) this.BNL(_, this.SRSo, true)
		}
	},
	TuESCls: function() {
		var A = this[$hR]();
		if(A) V7(this.el, this.L6i);
		else HMT(this.el, this.L6i);
		var _ = this.Gnmr.firstChild,
			$ = this.NsTj.firstChild;
		_.style.height = "auto";
		if(A) _.style.height = jQuery(_).outerHeight() + "px";
		$.style.height = "auto";
		if(A) $.style.height = jQuery($).outerHeight() + "px"
	},
	setFrozenStartColumn: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this[LdGz] = $;
		this._headerTableHeight = KwY(this.NL.firstChild);
		if(this[$hR]()) this.AN94();
		else this.BwzW();
		this.TuESCls();
		this[Fcv]();
		this.DYd.scrollLeft = this.NL.scrollLeft = this.H23I.scrollLeft = 0;
		this.QyZn()
	},
	getFrozenStartColumn: function() {
		return this[LdGz]
	},
	setFrozenEndColumn: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this[T94] = $;
		this._headerTableHeight = KwY(this.NL.firstChild);
		if(this[$hR]()) this.AN94();
		else this.BwzW();
		this.TuESCls();
		this[Fcv]();
		this.DYd.scrollLeft = this.NL.scrollLeft = this.H23I.scrollLeft = 0;
		this.QyZn()
	},
	getFrozenEndColumn: function() {
		return this[T94]
	},
	unFrozenColumns: function() {
		var $ = this.ERW;
		this.ERW = false;
		this[PZ](-1);
		this[ZNk](-1);
		this.ERW = $;
		this[Fcv]()
	},
	frozenColumns: function(_, A) {
		var $ = this.ERW;
		this.ERW = false;
		this[Dr]();
		this[PZ](_);
		this[ZNk](A);
		this.ERW = $;
		this[Fcv]()
	},
	PM: function($) {
		if(this[$hR]()) return;
		this.NL.scrollLeft = this.Gnmr.scrollLeft = this.NsTj.scrollLeft = this.H23I.scrollLeft
	},
	AdE: function($) {
		this.TuES()
	},
	TuES: function() {
		if(!this[$hR]()) return;
		var E = this[Mh5i](),
			G = this.DYd.scrollLeft,
			$ = this[T94],
			B = 0;
		for(var _ = $ + 1, F = E.length; _ < F; _++) {
			var C = E[_];
			if(!C.visible) continue;
			var A = this[B0rI](C);
			if(G <= B) break;
			$ = _;
			B += A
		}
		for(_ = 0, F = E.length; _ < F; _++) {
			C = E[_];
			delete C._hide;
			if(this[T94] < _ && _ <= $) C._hide = true
		}
		for(_ = 0, F = E.length; _ < F; _++) {
			C = E[_];
			if(_ < this.frozenStartColumn || (_ > this[T94] && _ < $)) this.Ulc(C, false);
			else this.Ulc(C, true)
		}
		var D = "width:100%;";
		if(this.DYd.offsetWidth < this.DYd.scrollWidth || !this.fitColumns) D = "width:0px";
		this.Fy(D);
		SmU(this.NL.firstChild, this._headerTableHeight);
		for(_ = this[T94] + 1, F = E.length; _ < F; _++) {
			C = E[_];
			if(!C.visible) continue;
			if(_ <= $) this.Ulc(C, false);
			else this.Ulc(C, true)
		}
		this.Bru();
		this.Fe(true)
	},
	BwzW: function() {
		var A = this[Mh5i]();
		for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$];
			delete _._hide;
			if(_.visible) this.Ulc(_, true);
			this.BNL(_, this.SRSo, false)
		}
		this.Fy();
		this.Fe(false)
	},
	Fe: function(B) {
		var D = this.data;
		for(var _ = 0, E = D.length; _ < E; _++) {
			var A = D[_],
				$ = this.Y2B(A);
			if($) if(B) {
				var C = 0;
				$.style.height = C + "px"
			} else $.style.height = ""
		}
	},
	_doGridLines: function() {
		if(this[Pv_b]) HMT(this.el, "mini-grid-hideVLine");
		else V7(this.el, "mini-grid-hideVLine");
		if(this[SNM]) HMT(this.el, "mini-grid-hideHLine");
		else V7(this.el, "mini-grid-hideHLine")
	},
	setShowHGridLines: function($) {
		if(this[SNM] != $) {
			this[SNM] = $;
			this._doGridLines();
			this[Fcv]()
		}
	},
	getShowHGridLines: function() {
		return this[SNM]
	},
	setShowVGridLines: function($) {
		if(this[Pv_b] != $) {
			this[Pv_b] = $;
			this._doGridLines();
			this[Fcv]()
		}
	},
	getShowVGridLines: function() {
		return this[Pv_b]
	},
	setShowFilterRow: function($) {
		if(this[PLQ] != $) {
			this[PLQ] = $;
			this.VNHkRows();
			this[Fcv]()
		}
	},
	getShowFilterRow: function() {
		return this[PLQ]
	},
	setShowSummaryRow: function($) {
		if(this[AMb] != $) {
			this[AMb] = $;
			this.VNHkRows();
			this[Fcv]()
		}
	},
	getShowSummaryRow: function() {
		return this[AMb]
	},
	R1X: function() {
		if(this[PSk] == false) return;
		var B = this.data;
		for(var _ = 0, C = B.length; _ < C; _++) {
			var A = B[_],
				$ = this.Y2B(A);
			if($) if(this[PSk] && _ % 2 == 1) V7($, this.FT4);
			else HMT($, this.FT4)
		}
	},
	setAllowAlternating: function($) {
		if(this[PSk] != $) {
			this[PSk] = $;
			this.R1X()
		}
	},
	getAllowAlternating: function() {
		return this[PSk]
	},
	setEnableHotTrack: function($) {
		if(this[U9] != $) this[U9] = $
	},
	getEnableHotTrack: function() {
		return this[U9]
	},
	setShowLoading: function($) {
		this.showLoading = $
	},
	setAllowCellWrap: function($) {
		if(this.allowCellWrap != $) this.allowCellWrap = $
	},
	getAllowCellWrap: function() {
		return this.allowCellWrap
	},
	setScrollTop: function($) {
		this.scrollTop = $;
		this.H23I.scrollTop = $
	},
	getScrollTop: function() {
		return this.H23I.scrollTop
	},
	setBodyStyle: function($) {
		this.bodyStyle = $;
		Gn(this.H23I, $)
	},
	getBodyStyle: function() {
		return this.bodyStyle
	},
	setBodyCls: function($) {
		this.bodyCls = $;
		V7(this.H23I, $)
	},
	getBodyCls: function() {
		return this.bodyCls
	},
	setFooterStyle: function($) {
		this.footerStyle = $;
		Gn(this.Yj, $)
	},
	getFooterStyle: function() {
		return this.footerStyle
	},
	setFooterCls: function($) {
		this.footerCls = $;
		V7(this.Yj, $)
	},
	getFooterCls: function() {
		return this.footerCls
	},
	setShowHeader: function($) {
		this.showHeader = $;
		this.VNHkRows();
		this[Fcv]()
	},
	setShowFooter: function($) {
		this[B8] = $;
		this.VNHkRows();
		this[Fcv]()
	},
	setAutoHideRowDetail: function($) {
		this.autoHideRowDetail = $
	},
	setAllowSortColumn: function($) {
		this[G1s] = $
	},
	getAllowSortColumn: function() {
		return this[G1s]
	},
	setAllowMoveColumn: function($) {
		this[J_9] = $
	},
	getAllowMoveColumn: function() {
		return this[J_9]
	},
	setAllowResizeColumn: function($) {
		this[Q3Gi] = $
	},
	getAllowResizeColumn: function() {
		return this[Q3Gi]
	},
	setSelectOnLoad: function($) {
		this.selectOnLoad = $
	},
	getSelectOnLoad: function() {
		return this.selectOnLoad
	},
	setAllowResize: function($) {
		this[JkX] = $;
		this.LDts.style.display = this[JkX] ? "" : "none"
	},
	getAllowResize: function() {
		return this[JkX]
	},
	_ERW: true,
	showAllRowDetail: function() {
		this._ERW = false;
		for(var $ = 0, A = this.data.length; $ < A; $++) {
			var _ = this.data[$];
			this[DGV](_)
		}
		this._ERW = true;
		this[Fcv]()
	},
	hideAllRowDetail: function() {
		this._ERW = false;
		for(var $ = 0, A = this.data.length; $ < A; $++) {
			var _ = this.data[$];
			if(this[_sF](_)) this[_1a](_)
		}
		this._ERW = true;
		this[Fcv]()
	},
	showRowDetail: function(_) {
		_ = this[Ykd](_);
		if(!_) return;
		var B = this[JRy](_);
		B.style.display = "";
		_._showDetail = true;
		var $ = this.Y2B(_);
		V7($, "mini-grid-expandRow");
		this.fire("showrowdetail", {
			record: _
		});
		if(this._ERW) this[Fcv]();
		var A = this
	},
	hideRowDetail: function(_) {
		var B = this.C85(_),
			A = document.getElementById(B);
		if(A) A.style.display = "none";
		delete _._showDetail;
		var $ = this.Y2B(_);
		HMT($, "mini-grid-expandRow");
		this.fire("hiderowdetail", {
			record: _
		});
		if(this._ERW) this[Fcv]()
	},
	toggleRowDetail: function($) {
		$ = this[Ykd]($);
		if(!$) return;
		if(grid[_sF]($)) grid[_1a]($);
		else grid[DGV]($)
	},
	isShowRowDetail: function($) {
		$ = this[Ykd]($);
		if(!$) return false;
		return !!$._showDetail
	},
	getRowDetailEl: function($) {
		$ = this[Ykd]($);
		if(!$) return null;
		var A = this.C85($),
			_ = document.getElementById(A);
		if(!_) _ = this.EqQ($);
		return _
	},
	getRowDetailCellEl: function($) {
		var _ = this[JRy]($);
		if(_) return _.cells[0]
	},
	EqQ: function($) {
		var A = this.Y2B($),
			B = this.C85($),
			_ = this[Mh5i]().length;
		jQuery(A).after("<tr id=\"" + B + "\" class=\"mini-grid-detailRow\"><td class=\"mini-grid-detailCell\" colspan=\"" + _ + "\"></td></tr>");
		this.Bru();
		return document.getElementById(B)
	},
	Xcs: function() {
		var D = this.H23I.firstChild.getElementsByTagName("tr")[0],
			B = D.getElementsByTagName("td"),
			A = 0;
		for(var _ = 0, C = B.length; _ < C; _++) {
			var $ = B[_];
			if($.style.display != "none") A++
		}
		return A
	},
	Bru: function() {
		var _ = jQuery(".mini-grid-detailRow", this.el),
			B = this.Xcs();
		for(var A = 0, C = _.length; A < C; A++) {
			var D = _[A],
				$ = D.firstChild;
			$.colSpan = B
		}
	},
	X5At: function() {
		for(var $ = 0, B = this.data.length; $ < B; $++) {
			var _ = this.data[$];
			if(_._showDetail == true) {
				var C = this.C85(_),
					A = document.getElementById(C);
				if(A) mini.layout(A)
			}
		}
	},
	K6: function() {
		for(var $ = 0, B = this.data.length; $ < B; $++) {
			var _ = this.data[$];
			if(_._editing == true) {
				var A = this.Y2B(_);
				if(A) mini.layout(A)
			}
		}
	},
	LFz: function($) {
		$.cancel = true;
		this.gotoPage($.pageIndex, $[FX_r])
	},
	setSizeList: function($) {
		if(!mini.isArray($)) return;
		this.pager.setSizeList($)
	},
	getSizeList: function() {
		return this.pager.getSizeList()
	},
	setPageSize: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this[FX_r] = $;
		if(this.pager) this.pager.update(this.pageIndex, this.pageSize, this[Ak])
	},
	getPageSize: function() {
		return this[FX_r]
	},
	setPageIndex: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this[JAYp] = $;
		if(this.pager) this.pager.update(this.pageIndex, this.pageSize, this[Ak])
	},
	getPageIndex: function() {
		return this[JAYp]
	},
	setShowPageSize: function($) {
		this.showPageSize = $;
		this.pager.setShowPageSize($)
	},
	getShowPageSize: function() {
		return this.showPageSize
	},
	setShowPageIndex: function($) {
		this.showPageIndex = $;
		this.pager.setShowPageIndex($)
	},
	getShowPageIndex: function() {
		return this.showPageIndex
	},
	setShowTotalCount: function($) {
		this.showTotalCount = $;
		this.pager.setShowTotalCount($)
	},
	getShowTotalCount: function() {
		return this.showTotalCount
	},
	pageIndex: 0,
	pageSize: 10,
	totalCount: 0,
	totalPage: 0,
	showPageSize: true,
	showPageIndex: true,
	showTotalCount: true,
	setTotalCount: function($) {
		this[Ak] = $;
		this.pager.setTotalCount($)
	},
	getTotalCount: function() {
		return this[Ak]
	},
	getTotalPage: function() {
		return this.totalPage
	},
	sortField: "",
	sortOrder: "",
	url: "",
	autoLoad: false,
	loadParams: null,
	ajaxAsync: true,
	ajaxMethod: "post",
	showLoading: true,
	resultAsData: false,
	checkSelectOnLoad: true,
	setCheckSelectOnLoad: function($) {
		this[TL9] = $
	},
	getCheckSelectOnLoad: function() {
		return this[TL9]
	},
	KuR: "total",
	_dataField: "data",
	AEr: function($) {
		return $.data
	},
	YqFk: function(_, B, C) {
		_ = _ || {};
		if(mini.isNull(_[JAYp])) _[JAYp] = 0;
		if(mini.isNull(_[FX_r])) _[FX_r] = this[FX_r];
		_.sortField = this.sortField;
		_.sortOrder = this.sortOrder;
		this.loadParams = _;
		if(this.showLoading) this.loading();
		var A = this.url,
			E = this.ajaxMethod;
		if(A) if(A.indexOf(".txt") != -1 || A.indexOf(".json") != -1) E = "get";
		var D = {
			url: A,
			async: this.ajaxAsync,
			type: E,
			params: _,
			cancel: false
		};
		this.fire("beforeload", D);
		if(D.cancel == true) return;
		this.YFValue = this.YF ? this.YF[this.idField] : null;
		var $ = this;
		this.HIU = jQuery.ajax({
			url: D.url,
			async: D.async,
			data: D.params,
			type: D.type,
			cache: false,
			dataType: "text",
			success: function(F, D, C) {
				var J = null;
				try {
					J = mini.decode(F)
				} catch(K) {}
				if(J == null) J = {
					data: [],
					total: 0
				};
				$.unmask();
				if(mini.isNumber(J.error) && J.error != 0) {
					var L = {
						errorCode: J.error,
						xmlHttp: C,
						errorMsg: J.errorMsg,
						result: J
					};
					$.fire("loaderror", L);
					return
				}
				if($[EeuC] || mini.isArray(J)) {
					var G = {};
					G[$.KuR] = J.length;
					G.data = J;
					J = G
				}
				var E = parseInt(J[$.KuR]),
					I = $.AEr(J);
				if(mini.isNumber(_[JAYp])) $[JAYp] = _[JAYp];
				if(mini.isNumber(_[FX_r])) $[FX_r] = _[FX_r];
				if(mini.isNumber(E)) $[Ak] = E;
				var K = {
					result: J,
					data: I,
					total: E,
					cancel: false
				};
				$.fire("preload", K);
				if(K.cancel == true) return;
				var H = $.ERW;
				$.ERW = false;
				$[M3b](K.data);
				if($.YFValue && $[TL9]) {
					var A = $[V2c]($.YFValue);
					if(A) $[M$](A);
					else $[KO2]()
				} else if($.YF) $[KO2]();
				if($[JFP]() == null && $.selectOnLoad && $.data.length > 0) $[M$](0);
				$.fire("load", K);
				if(B) B[GkN]($, J);
				$.ERW = H;
				$[Fcv]()
			},
			error: function(_, B, A) {
				if(C) C[GkN](scope, _);
				var D = {
					xmlHttp: _,
					errorMsg: _.responseText,
					errorCode: B
				};
				$.fire("loaderror", D);
				$.unmask()
			}
		})
	},
	load: function(_, A, B) {
		if(this._loadTimer) clearTimeout(this._loadTimer);
		var $ = this;
		this[Etn]();
		this.loadParams = _ || {};
		if(this.ajaxAsync) this._loadTimer = setTimeout(function() {
			$.YqFk(_, A, B)
		}, 1);
		else $.YqFk(_, A, B)
	},
	reload: function(_, $) {
		this.load(this.loadParams, _, $)
	},
	gotoPage: function($, A) {
		var _ = this.loadParams || {};
		if(mini.isNumber($)) _[JAYp] = $;
		if(mini.isNumber(A)) _[FX_r] = A;
		this.load(_)
	},
	sortBy: function(A, _) {
		this.sortField = A;
		this.sortOrder = _ == "asc" ? "asc" : "desc";
		var $ = this.loadParams || {};
		$.sortField = A;
		$.sortOrder = _;
		$[JAYp] = this[JAYp];
		this.load($)
	},
	clearSort: function() {
		this.sortField = "";
		this.sortOrder = "";
		this.reload()
	},
	allowCellSelect: false,
	allowCellEdit: false,
	CGq: "mini-grid-cell-selected",
	Zg_Z: null,
	LBaf: null,
	Maqf: null,
	Mv: null,
	THr: function(B) {
		if(this.Zg_Z) {
			var $ = this.Zg_Z[0],
				A = this.Zg_Z[1],
				_ = this._jX($, A);
			if(_) if(B) V7(_, this.CGq);
			else HMT(_, this.CGq)
		}
	},
	setCurrentCell: function($) {
		if(this.Zg_Z != $) {
			this.THr(false);
			this.Zg_Z = $;
			this.THr(true);
			if($) this[JSYh]($[0], $[1]);
			this.fire("currentcellchanged")
		}
	},
	getCurrentCell: function() {
		var $ = this.Zg_Z;
		if($) if(this.data.indexOf($[0]) == -1) {
			this.Zg_Z = null;
			$ = null
		}
		return $
	},
	setAllowCellSelect: function($) {
		this[SR] = $
	},
	getAllowCellSelect: function($) {
		return this[SR]
	},
	setAllowCellEdit: function($) {
		this[SjLH] = $
	},
	getAllowCellEdit: function($) {
		return this[SjLH]
	},
	beginEditCell: function() {
		var A = this.getCurrentCell();
		if(this.LBaf && A) if(this.LBaf[0] == A[0] && this.LBaf[1] == A[1]) return;
		if(this.LBaf) this.commitEdit();
		if(A) {
			var $ = A[0],
				_ = A[1],
				B = this.HOL($, _, this[UIk](_));
			if(B !== false) {
				this.LBaf = A;
				this.Zv($, _)
			}
		}
	},
	cancelEdit: function() {
		if(this[SjLH]) {
			if(this.LBaf) this.S5V()
		} else if(this[REM]()) {
			this.ERW = false;
			var A = this.data.clone();
			for(var $ = 0, B = A.length; $ < B; $++) {
				var _ = A[$];
				if(_._editing == true) this[LFM]($)
			}
			this.ERW = true;
			this[Fcv]()
		}
	},
	commitEdit: function() {
		if(this[SjLH]) {
			if(this.LBaf) {
				this.QV(this.LBaf[0], this.LBaf[1]);
				this.S5V()
			}
		} else if(this[REM]()) {
			this.ERW = false;
			var A = this.data.clone();
			for(var $ = 0, B = A.length; $ < B; $++) {
				var _ = A[$];
				if(_._editing == true) this[FfrM]($)
			}
			this.ERW = true;
			this[Fcv]()
		}
	},
	getCellEditor: function(_, $) {
		if(this[SjLH]) {
			var B = mini.getAndCreate(_.editor);
			if(B && B != _.editor) _.editor = B;
			return B
		} else {
			$ = this[Ykd]($);
			_ = this[ZUT$](_);
			if(!$) $ = this.getEditingRow();
			if(!$ || !_) return null;
			var A = this.uid + "$" + $._uid + "$" + _.name + "$editor";
			return mini.get(A)
		}
	},
	HOL: function($, A, C) {
		var B = {
			sender: this,
			rowIndex: this.data.indexOf($),
			row: $,
			record: $,
			column: A,
			field: A.field,
			editor: C,
			value: $[A.field],
			cancel: false
		};
		this.fire("cellbeginedit", B);
		var C = B.editor;
		value = B.value;
		if(B.cancel) return false;
		if(!C) return false;
		if(mini.isNull(value)) value = "";
		if(C[UD7]) C[UD7](value);
		C.ownerRowID = $._uid;
		if(A.displayField && C[Chg]) {
			var _ = $[A.displayField];
			C[Chg](_)
		}
		if(this[SjLH]) this.Maqf = B.editor;
		return true
	},
	QV: function(_, A, C) {
		var B = {
			sender: this,
			record: _,
			row: _,
			column: A,
			field: A.field,
			editor: C ? C : this[UIk](A),
			value: "",
			text: "",
			cancel: false
		};
		if(B.editor && B.editor.getValue) B.value = B.editor.getValue();
		if(B.editor && B.editor.getText) B.text = B.editor.getText();
		this.fire("cellcommitedit", B);
		if(B.cancel == false) if(this[SjLH]) {
			var $ = {};
			$[A.field] = B.value;
			if(A.displayField) $[A.displayField] = B.text;
			this[W01](_, $)
		}
		return B
	},
	S5V: function() {
		if(!this.LBaf) return;
		var _ = this.LBaf[0],
			C = this.LBaf[1],
			E = {
				sender: this,
				record: _,
				row: _,
				column: C,
				field: C.field,
				editor: this.Maqf,
				value: _[C.field]
			};
		this.fire("cellendedit", E);
		if(this[SjLH]) {
			var D = E.editor;
			if(D && D.setIsValid) D.setIsValid(true);
			if(this.Mv) this.Mv.style.display = "none";
			var A = this.Mv.childNodes;
			for(var $ = A.length - 1; $ >= 0; $--) {
				var B = A[$];
				this.Mv.removeChild(B)
			}
			if(D && D[OsRe]) D[OsRe]();
			if(D && D[UD7]) D[UD7]("");
			this.Maqf = null;
			this.LBaf = null
		}
	},
	Zv: function(_, C) {
		if(!this.Maqf) return false;
		var $ = this[OP4p](_, C),
			E = {
				sender: this,
				record: _,
				row: _,
				column: C,
				field: C.field,
				cellBox: $,
				editor: this.Maqf
			};
		this.fire("cellshowingedit", E);
		var D = E.editor;
		if(D && D.setIsValid) D.setIsValid(true);
		var B = this.MIIH($);
		if(D[Ivp]) {
			D[Ivp](this.Mv);
			setTimeout(function() {
				D.focus();
				if(D[OJs]) D[OJs]()
			}, 10);
			if(D[YKu]) D[YKu](true)
		} else if(D.el) {
			this.Mv.appendChild(D.el);
			setTimeout(function() {
				try {
					D.el.focus()
				} catch($) {}
			}, 10)
		}
		if(D[_wJ]) {
			var A = $.width;
			if(A < 50) A = 50;
			D[_wJ](A)
		}
		$v9(document, "mousedown", this.OvM, this);
		if(C.autoShowPopup && D[Zpt]) D[Zpt]()
	},
	OvM: function(C) {
		if(this.Maqf) {
			var A = this.JTp(C);
			if(this.LBaf && A) if(this.LBaf[0] == A.record && this.LBaf[1] == A.column) return false;
			var _ = false;
			if(this.Maqf[LU]) _ = this.Maqf[LU](C);
			else _ = TWAc(this.Mv, C.target);
			if(_ == false) {
				var B = this;
				if(TWAc(this.H23I, C.target) == false) setTimeout(function() {
					B.commitEdit()
				}, 1);
				else {
					var $ = B.LBaf;
					setTimeout(function() {
						var _ = B.LBaf;
						if($ == _) B.commitEdit()
					}, 70)
				}
				M4(document, "mousedown", this.OvM, this)
			}
		}
	},
	MIIH: function($) {
		if(!this.Mv) {
			this.Mv = mini.append(document.body, "<div class=\"mini-grid-editwrap\" style=\"position:absolute;\"></div>");
			$v9(this.Mv, "keydown", this.POgL, this)
		}
		this.Mv.style.zIndex = 1000000000;
		this.Mv.style.display = "block";
		mini[Wib](this.Mv, $.x, $.y);
		Sbkj(this.Mv, $.width);
		return this.Mv
	},
	POgL: function(A) {
		var _ = this.Maqf;
		if(A.keyCode == 13 && A.ctrlKey == false && _ && _.type == "textarea") return;
		if(A.keyCode == 13) {
			var $ = this.LBaf;
			if($ && $[1] && $[1].enterCommit === false) return;
			this.commitEdit();
			this.focus()
		} else if(A.keyCode == 27) {
			this[Etn]();
			this.focus()
		} else if(A.keyCode == 9) this[Etn]()
	},
	getEditorOwnerRow: function(_) {
		var $ = _.ownerRowID;
		return this.getRowByUID($)
	},
	beginEditRow: function(row) {
		if(this[SjLH]) return;
		var sss = new Date();
		row = this[Ykd](row);
		if(!row) return;
		var rowEl = this.Y2B(row);
		if(!rowEl) return;
		row._editing = true;
		var s = this.SnP(row),
			rowEl = this.Y2B(row);
		jQuery(rowEl).before(s);
		rowEl.parentNode.removeChild(rowEl);
		rowEl = this.Y2B(row);
		V7(rowEl, "mini-grid-rowEdit");
		var columns = this[Mh5i]();
		for(var i = 0, l = columns.length; i < l; i++) {
			var column = columns[i],
				value = row[column.field],
				cellId = this.SjTu(row, columns[i]),
				cellEl = document.getElementById(cellId);
			if(!cellEl) continue;
			if(typeof column.editor == "string") column.editor = eval("(" + column.editor + ")");
			var editorConfig = mini.copyTo({}, column.editor);
			editorConfig.id = this.uid + "$" + row._uid + "$" + column.name + "$editor";
			var editor = mini.create(editorConfig);
			if(this.HOL(row, column, editor)) if(editor) {
				V7(cellEl, "mini-grid-cellEdit");
				cellEl.innerHTML = "";
				cellEl.appendChild(editor.el);
				V7(editor.el, "mini-grid-editor")
			}
		}
		this[Fcv]()
	},
	cancelEditRow: function(B) {
		if(this[SjLH]) return;
		B = this[Ykd](B);
		if(!B || !B._editing) return;
		delete B._editing;
		var _ = this.Y2B(B),
			D = this[Mh5i]();
		for(var $ = 0, F = D.length; $ < F; $++) {
			var C = D[$],
				H = this.SjTu(B, D[$]),
				A = document.getElementById(H),
				E = A.firstChild,
				I = mini.get(E);
			if(!I) continue;
			I[EqU5]()
		}
		var G = this.SnP(B);
		jQuery(_).before(G);
		_.parentNode.removeChild(_);
		this[Fcv]()
	},
	commitEditRow: function($) {
		if(this[SjLH]) return;
		$ = this[Ykd]($);
		if(!$ || !$._editing) return;
		var _ = this[U_]($);
		this.W9 = false;
		this[W01]($, _);
		this.W9 = true;
		this[LFM]($)
	},
	isEditing: function() {
		for(var $ = 0, A = this.data.length; $ < A; $++) {
			var _ = this.data[$];
			if(_._editing == true) return true
		}
		return false
	},
	isEditingRow: function($) {
		$ = this[Ykd]($);
		if(!$) return false;
		return !!$._editing
	},
	isNewRow: function($) {
		return $._state == "added"
	},
	getEditingRows: function() {
		var A = [];
		for(var $ = 0, B = this.data.length; $ < B; $++) {
			var _ = this.data[$];
			if(_._editing == true) A.push(_)
		}
		return A
	},
	getEditingRow: function() {
		var $ = this.getEditingRows();
		return $[0]
	},
	getEditData: function(C) {
		var B = [];
		for(var $ = 0, D = this.data.length; $ < D; $++) {
			var _ = this.data[$];
			if(_._editing == true) {
				var A = this[U_]($, C);
				A._index = $;
				B.push(A)
			}
		}
		return B
	},
	getEditRowData: function(G, I) {
		G = this[Ykd](G);
		if(!G || !G._editing) return null;
		var H = {},
			B = this[Mh5i]();
		for(var F = 0, C = B.length; F < C; F++) {
			var A = B[F],
				D = this.SjTu(G, B[F]),
				_ = document.getElementById(D),
				J = _.firstChild,
				E = mini.get(J);
			if(!E) continue;
			var K = this.QV(G, A, E);
			H[A.field] = K.value;
			if(A.displayField) H[A.displayField] = K.text
		}
		H[this.idField] = G[this.idField];
		if(I) {
			var $ = mini.copyTo({}, G);
			H = mini.copyTo($, H)
		}
		return H
	},
	getChanges: function(B) {
		var A = [];
		if(!B || B == "removed") A.addRange(this.YNn);
		for(var $ = 0, C = this.data.length; $ < C; $++) {
			var _ = this.data[$];
			if(_._state && (!B || B == _._state)) A.push(_)
		}
		return A
	},
	isChanged: function() {
		var $ = this.getChanges();
		return $.length > 0
	},
	L205: "_uid",
	LwB: function($) {
		var A = $[this.L205],
			_ = this.VNi[A];
		if(!_) _ = this.VNi[A] = {};
		return _
	},
	FiTN: function(A, _) {
		var $ = this.VNi[A[this.L205]];
		if(!$) return false;
		if(mini.isNull(_)) return false;
		return $.hasOwnProperty(_)
	},
	Ym76: function(A, B) {
		var E = false;
		for(var C in B) {
			var $ = B[C],
				D = A[C];
			if(mini[B31](D, $)) continue;
			A[C] = $;
			if(A._state != "added") {
				A._state = "modified";
				var _ = this.LwB(A);
				if(!_.hasOwnProperty(C)) _[C] = D
			}
			E = true
		}
		return E
	},
	W9: true,
	updateRow: function(_, A) {
		_ = this[Ykd](_);
		if(!_ || !A) return;
		var C = this.Ym76(_, A);
		if(C == false) return;
		if(this.W9) {
			var B = this,
				D = B.SnP(_),
				$ = B.Y2B(_);
			jQuery($).before(D);
			$.parentNode.removeChild($)
		}
		if(_._state == "modified") this.fire("updaterow", {
			record: _,
			row: _
		});
		if(_ == this[JFP]()) this.R$u(_);
		this.WU$Y()
	},
	deleteRows: function(_) {
		if(!mini.isArray(_)) return;
		_ = _.clone();
		for(var $ = 0, A = _.length; $ < A; $++) this.deleteRow(_[$])
	},
	deleteRow: function(_) {
		_ = this[Ykd](_);
		if(!_ || _._state == "deleted") return;
		if(_._state == "added") this[NP](_, true);
		else {
			if(this.isEditingRow(_)) this[LFM](_);
			_._state = "deleted";
			var $ = this.Y2B(_);
			V7($, "mini-grid-deleteRow");
			this.fire("deleterow", {
				record: _,
				row: _
			})
		}
	},
	removeRows: function(_, B) {
		if(!mini.isArray(_)) return;
		_ = _.clone();
		for(var $ = 0, A = _.length; $ < A; $++) this[NP](_[$], B)
	},
	removeRow: function(A, H) {
		A = this[Ykd](A);
		if(!A) return;
		var D = A == this[JFP](),
			C = this[Jm](A),
			$ = this.data.indexOf(A);
		this.data.remove(A);
		if(A._state != "added") {
			A._state = "removed";
			this.YNn.push(A);
			delete this.VNi[A[this.L205]]
		}
		delete this.Cy[A._uid];
		var G = this.SnP(A),
			_ = this.Y2B(A);
		if(_) _.parentNode.removeChild(_);
		var F = this.C85(A),
			E = document.getElementById(F);
		if(E) E.parentNode.removeChild(E);
		if(C && H) {
			var B = this.getAt($);
			if(!B) B = this.getAt($ - 1);
			this[KO2]();
			this[M$](B)
		}
		this.B42d();
		this.fire("removerow", {
			record: A,
			row: A
		});
		if(D) this.R$u(A);
		this.R1X();
		this.WU$Y()
	},
	autoCreateNewID: false,
	addRows: function(A, $) {
		if(!mini.isArray(A)) return;
		A = A.clone();
		for(var _ = 0, B = A.length; _ < B; _++) this.addRow(A[_], $)
	},
	addRow: function(A, $) {
		if(mini.isNull($)) $ = this.data.length;
		$ = this.indexOf($);
		var B = this[Ykd]($);
		this.data.insert($, A);
		if(!A[this.idField]) {
			if(this.autoCreateNewID) A[this.idField] = UUID();
			var D = {
				row: A,
				record: A
			};
			this.fire("beforeaddrow", D)
		}
		A._state = "added";
		delete this.Cy[A._uid];
		A._uid = Jcq++;
		this.Cy[A._uid] = A;
		var C = this.SnP(A);
		if(B) {
			var _ = this.Y2B(B);
			jQuery(_).before(C)
		} else mini.append(this.H23I.firstChild, C);
		this.R1X();
		this.WU$Y();
		this.fire("addrow", {
			record: A,
			row: A
		})
	},
	moveRow: function(B, _) {
		B = this[Ykd](B);
		if(!B) return;
		if(_ < 0) return;
		if(_ > this.data.length) return;
		var D = this[Ykd](_);
		if(B == D) return;
		this.data.remove(B);
		var A = this.Y2B(B);
		if(D) {
			_ = this.data.indexOf(D);
			this.data.insert(_, B);
			var C = this.Y2B(D);
			jQuery(C).before(A)
		} else {
			this.data.insert(this.data.length, B);
			var $ = this.H23I.firstChild;
			mini.append($.firstChild || $, A)
		}
		this.R1X();
		this.WU$Y();
		this[JSYh](B);
		this.fire("moverow", {
			record: B,
			row: B,
			index: _
		})
	},
	clearRows: function() {
		this.data = [];
		this[Mdk]()
	},
	indexOf: function($) {
		if(typeof $ == "number") return $;
		return this.data.indexOf($)
	},
	getAt: function($) {
		return this.data[$]
	},
	getRow: function($) {
		var _ = typeof $;
		if(_ == "number") return this.data[$];
		else if(_ == "object") return $
	},
	getRowByValue: function(A) {
		for(var _ = 0, B = this.data.length; _ < B; _++) {
			var $ = this.data[_];
			if($[this.idField] == A) return $
		}
	},
	getRowByUID: function($) {
		return this.Cy[$]
	},
	findRows: function(C) {
		var A = [];
		if(C) for(var $ = 0, B = this.data.length; $ < B; $++) {
			var _ = this.data[$];
			if(C(_) === true) A.push(_)
		}
		return A
	},
	findRow: function(B) {
		if(B) for(var $ = 0, A = this.data.length; $ < A; $++) {
			var _ = this.data[$];
			if(B(_) === true) return _
		}
	},
	showGroupSummary: false,
	setShowGroupSummary: function($) {
		this.showGroupSummary = $
	},
	getShowGroupSummary: function() {
		return this.showGroupSummary
	},
	Qdw: 1,
	Idp: "",
	UNW: "",
	groupBy: function($, _) {
		if(!$) return;
		this.Idp = $;
		if(typeof _ == "string") _ = _.toLowerCase();
		this.UNW = _;
		this.DY = null;
		this[Mdk]()
	},
	clearGroup: function() {
		this.Idp = "";
		this.UNW = "";
		this.DY = null;
		this[Mdk]()
	},
	getGroupField: function() {
		return this.Idp
	},
	getGroupDir: function() {
		return this.UNW
	},
	isGrouping: function() {
		return this.Idp != ""
	},
	H_: function() {
		if(this[BX1N]() == false) return null;
		this.DY = null;
		if(!this.DY) {
			var F = this.Idp,
				H = this.UNW,
				D = this.data.clone();
			if(typeof H == "function") mini.sort(D, H);
			else {
				mini.sort(D, function(_, B) {
					var $ = _[F],
						A = B[F];
					if($ > A) return 1;
					else return 0
				}, this);
				if(H == "desc") D.resvert()
			}
			var B = [],
				C = {};
			for(var _ = 0, G = D.length; _ < G; _++) {
				var $ = D[_],
					I = $[F],
					E = mini.isDate(I) ? I.getTime() : I,
					A = C[E];
				if(!A) {
					A = C[E] = {};
					A.field = F, A.dir = H;
					A.value = I;
					A.rows = [];
					B.push(A);
					A.id = this.Qdw++
				}
				A.rows.push($)
			}
			this.DY = B
		}
		return this.DY
	},
	$g9: function(C) {
		if(!this.DY) return null;
		var A = this.DY;
		for(var $ = 0, B = A.length; $ < B; $++) {
			var _ = A[$];
			if(_.id == C) return _
		}
	},
	OlT: function($) {
		var _ = {
			group: $,
			rows: $.rows,
			field: $.field,
			dir: $.dir,
			value: $.value,
			cellHtml: $.field + " :" + $.value
		};
		this.fire("drawgroup", _);
		return _
	},
	onDrawGroupHeader: function(_, $) {
		this.on("drawgroupheader", _, $)
	},
	onDrawGroupSummary: function(_, $) {
		this.on("drawgroupsummary", _, $)
	},
	margeCells: function(F) {
		if(!mini.isArray(F)) return;
		for(var $ = 0, D = F.length; $ < D; $++) {
			var B = F[$];
			if(!B.rowSpan) B.rowSpan = 1;
			if(!B.colSpan) B.colSpan = 1;
			var E = this.Fb(B.rowIndex, B.columnIndex, B.rowSpan, B.colSpan);
			for(var C = 0, _ = E.length; C < _; C++) {
				var A = E[C];
				if(C != 0) A.style.display = "none";
				else {
					A.rowSpan = B.rowSpan;
					A.colSpan = B.colSpan
				}
			}
		}
	},
	Fb: function(I, E, A, B) {
		var J = [];
		if(!mini.isNumber(I)) return [];
		if(!mini.isNumber(E)) return [];
		var C = this[Mh5i](),
			G = this.data;
		for(var F = I, D = I + A; F < D; F++) for(var H = E, $ = E + B; H < $; H++) {
			var _ = this._jX(F, H);
			if(_) J.push(_)
		}
		return J
	},
	YF: null,
	MyQ: [],
	B42d: function() {
		var A = this.MyQ;
		for(var $ = A.length - 1; $ >= 0; $--) {
			var _ = A[$];
			if( !! this.Cy[_._uid] == false) {
				A.removeAt($);
				delete this.Sj$W[_._uid]
			}
		}
		if(this.YF) if( !! this.Sj$W[this.YF._uid] == false) this.YF = null
	},
	setAllowRowSelect: function($) {
		this[R4v] = $
	},
	getAllowRowSelect: function($) {
		return this[R4v]
	},
	setMultiSelect: function($) {
		if(this[V0] != $) {
			this[V0] = $;
			this.Fy()
		}
	},
	isSelected: function($) {
		$ = this[Ykd]($);
		if(!$) return false;
		return !!this.Sj$W[$._uid]
	},
	getSelecteds: function() {
		this.B42d();
		return this.MyQ.clone()
	},
	setCurrent: function($) {
		this[UXi]($)
	},
	getCurrent: function() {
		return this[JFP]()
	},
	getSelected: function() {
		this.B42d();
		return this.YF
	},
	scrollIntoView: function(A, B) {
		try {
			if(B) {
				var _ = this._jX(A, B);
				mini[JSYh](_, this.H23I, true)
			} else {
				var $ = this.Y2B(A);
				mini[JSYh]($, this.H23I, false)
			}
		} catch(C) {}
	},
	setSelected: function($) {
		if($) this[M$]($);
		else this[R4rm](this.YF);
		if(this.YF) this[JSYh](this.YF);
		this.Fdz()
	},
	select: function($) {
		$ = this[Ykd]($);
		if(!$) return;
		this.YF = $;
		this[SsB]([$])
	},
	deselect: function($) {
		$ = this[Ykd]($);
		if(!$) return;
		this[Q_AF]([$])
	},
	selectAll: function() {
		var $ = this.data.clone();
		this[SsB]($)
	},
	deselectAll: function() {
		var $ = this.MyQ.clone();
		this.YF = null;
		this[Q_AF]($)
	},
	clearSelect: function() {
		this[KO2]()
	},
	selects: function(A) {
		if(!A || A.length == 0) return;
		A = A.clone();
		this.Lbq(A, true);
		for(var _ = 0, B = A.length; _ < B; _++) {
			var $ = A[_];
			if(!this[Jm]($)) {
				this.MyQ.push($);
				this.Sj$W[$._uid] = $
			}
		}
		this.WeH()
	},
	deselects: function(A) {
		if(!A) A = [];
		A = A.clone();
		this.Lbq(A, false);
		for(var _ = A.length - 1; _ >= 0; _--) {
			var $ = A[_];
			if(this[Jm]($)) {
				this.MyQ.remove($);
				delete this.Sj$W[$._uid]
			}
		}
		if(A.indexOf(this.YF) != -1) this.YF = null;
		this.WeH()
	},
	Lbq: function(A, D) {
		var B = new Date();
		for(var _ = 0, C = A.length; _ < C; _++) {
			var $ = A[_];
			if(D) this[GCXn]($, this.S9v);
			else this[T2cA]($, this.S9v)
		}
	},
	WeH: function() {
		if(this.B9j) clearTimeout(this.B9j);
		var $ = this;
		this.B9j = setTimeout(function() {
			var _ = {
				selecteds: $.getSelecteds(),
				selected: $[JFP]()
			};
			$.fire("SelectionChanged", _);
			$.R$u(_.selected)
		}, 1)
	},
	R$u: function($) {
		if(this._currentTimer) clearTimeout(this._currentTimer);
		var _ = this;
		this._currentTimer = setTimeout(function() {
			var A = {
				record: $,
				row: $
			};
			_.fire("CurrentChanged", A);
			_._currentTimer = null
		}, 1)
	},
	addRowCls: function(_, A) {
		var $ = this.Y2B(_);
		if($) V7($, A)
	},
	removeRowCls: function(_, A) {
		var $ = this.Y2B(_);
		if($) HMT($, A)
	},
	_0K: function(_, $) {
		_ = this[Ykd](_);
		if(!_ || _ == this.JqD) return;
		var A = this.Y2B(_);
		if($ && A) this[JSYh](_);
		if(this.JqD == _) return;
		this.Fdz();
		this.JqD = _;
		V7(A, this.FML)
	},
	Fdz: function() {
		if(!this.JqD) return;
		var $ = this.Y2B(this.JqD);
		if($) HMT($, this.FML);
		this.JqD = null
	},
	UoCj: function(B) {
		var A = ZW(B.target, this.Sz4J);
		if(!A) return null;
		var $ = A.id.split("$"),
			_ = $[$.length - 1];
		return this.getRowByUID(_)
	},
	N88: function(B) {
		var _ = ZW(B.target, "mini-grid-cell");
		if(!_) _ = ZW(B.target, "mini-grid-headerCell");
		if(_) {
			var $ = _.id.split("$"),
				A = $[$.length - 1];
			return this.YD(A)
		}
		return null
	},
	JTp: function(A) {
		var $ = this.UoCj(A),
			_ = this.N88(A);
		return {
			record: $,
			column: _
		}
	},
	getColumnBox: function(_) {
		var A = this.Iu0b(_),
			$ = document.getElementById(A);
		if($) return Y3L($)
	},
	W8: function(C, A) {
		if(this[SjLH]) this.commitEdit();
		var B = jQuery(this.H23I).css("overflow-y");
		if(B == "hidden") {
			var $ = C.wheelDelta || -C.detail * 24,
				_ = this.H23I.scrollTop;
			_ -= $;
			this.H23I.scrollTop = _;
			if(_ == this.H23I.scrollTop) C.preventDefault();
			var C = {
				scrollTop: this.H23I.scrollTop,
				direction: "vertical"
			};
			this.fire("scroll", C)
		}
	},
	PGY: function(A) {
		var _ = ZW(A.target, "mini-grid-groupRow");
		if(_) {
			var $ = this.$g9(_.id);
			if($) {
				$.expanded = !($.expanded === false ? false : true);
				if($.expanded) HMT(_, "mini-grid-group-collapse");
				else V7(_, "mini-grid-group-collapse");
				if($.expanded) this.C53o($);
				else this.RMT($)
			}
		} else this.E4yM(A, "Click")
	},
	RMT: function(A) {
		var C = A.rows;
		for(var _ = 0, D = C.length; _ < D; _++) {
			var B = C[_],
				$ = this.Y2B(B);
			if($) $.style.display = "none"
		}
		this[Fcv]()
	},
	C53o: function(A) {
		var C = A.rows;
		for(var _ = 0, D = C.length; _ < D; _++) {
			var B = C[_],
				$ = this.Y2B(B);
			if($) $.style.display = ""
		}
		this[Fcv]()
	},
	Esh: function($) {
		this.E4yM($, "Dblclick")
	},
	AOlf: function($) {
		this.E4yM($, "MouseDown");
		if(TWAc(this.Gnmr, $.target) || TWAc(this.NsTj, $.target) || TWAc(this.Yj, $.target) || ZW($.target, "mini-grid-rowEdit"));
		else this.focus()
	},
	Hfq: function($) {
		this.E4yM($, "MouseUp")
	},
	VeS: function($) {
		this.E4yM($, "MouseMove")
	},
	LZR: function($) {
		this.E4yM($, "MouseOver")
	},
	$MHa: function($) {
		this.E4yM($, "MouseOut")
	},
	HtB: function($) {
		this.E4yM($, "KeyDown")
	},
	O83: function($) {
		this.E4yM($, "KeyUp")
	},
	M35: function($) {
		this.E4yM($, "ContextMenu")
	},
	E4yM: function(F, D) {
		if(!this.enabled) return;
		var C = this.JTp(F),
			_ = C.record,
			B = C.column;
		if(_) {
			var A = {
				record: _,
				row: _,
				htmlEvent: F
			},
				E = this["_OnRow" + D];
			if(E) E[GkN](this, A);
			else this.fire("row" + D, A)
		}
		if(B) {
			A = {
				column: B,
				field: B.field,
				htmlEvent: F
			}, E = this["_OnColumn" + D];
			if(E) E[GkN](this, A);
			else this.fire("column" + D, A)
		}
		if(_ && B) {
			A = {
				sender: this,
				record: _,
				row: _,
				column: B,
				field: B.field,
				htmlEvent: F
			}, E = this["_OnCell" + D];
			if(E) E[GkN](this, A);
			else this.fire("cell" + D, A);
			if(B["onCell" + D]) B["onCell" + D][GkN](B, A)
		}
		if(!_ && B) {
			A = {
				column: B,
				htmlEvent: F
			}, E = this["_OnHeaderCell" + D];
			if(E) E[GkN](this, A);
			else {
				var $ = "onheadercell" + D.toLowerCase();
				if(B[$]) {
					A.sender = this;
					B[$](A)
				}
				this.fire("headercell" + D, A)
			}
		}
		if(!_) this.Fdz()
	},
	GgLg: function($, B, C, D) {
		var _ = $[B.field],
			E = {
				sender: this,
				rowIndex: C,
				columnIndex: D,
				record: $,
				row: $,
				column: B,
				field: B.field,
				value: _,
				cellHtml: _,
				rowCls: null,
				cellCls: B.cellCls || "",
				rowStyle: null,
				cellStyle: B.cellStyle || "",
				allowCellWrap: this.allowCellWrap
			};
		if(B.dateFormat) if(mini.isDate(E.value)) E.cellHtml = mini.formatDate(_, B.dateFormat);
		else E.cellHtml = _;
		if(B.displayField) E.cellHtml = $[B.displayField];
		var A = B.renderer;
		if(A) {
			fn = typeof A == "function" ? A : window[A];
			if(fn) E.cellHtml = fn[GkN](B, E)
		}
		this.fire("drawcell", E);
		if(E.cellHtml === null || E.cellHtml === undefined || E.cellHtml === "") E.cellHtml = "&nbsp;";
		return E
	},
	_OnCellMouseDown: function(_) {
		var $ = _.record;
		if($.enabled === false) return;
		this.fire("cellmousedown", _)
	},
	_OnRowMouseOut: function($) {
		if(!this.enabled) return;
		if(TWAc(this.el, $.target)) return
	},
	_OnRowMouseMove: function(_) {
		record = _.record;
		if(!this.enabled || record.enabled === false || this[U9] == false) return;
		this.fire("rowmousemove", _);
		var $ = this;
		$._0K(record)
	},
	_OnHeaderCellClick: function(A) {
		A.sender = this;
		var $ = A.column;
		if(!XPZ(A.htmlEvent.target, "mini-grid-splitter")) {
			if(this[G1s] && this[REM]() == false) if(!$.columns || $.columns.length == 0) if($.field && $.allowSort !== false) {
				var _ = "asc";
				if(this.sortField == $.field) _ = this.sortOrder == "asc" ? "desc" : "asc";
				this.sortBy($.field, _)
			}
			this.fire("headercellclick", A)
		}
	},
	__OnHtmlContextMenu: function(_) {
		var $ = {
			popupEl: this.el,
			htmlEvent: _,
			cancel: false
		};
		if(TWAc(this.NL, _.target)) {
			if(this.headerContextMenu) {
				this.headerContextMenu.fire("BeforeOpen", $);
				if($.cancel == true) return;
				this.headerContextMenu.fire("opening", $);
				if($.cancel == true) return;
				this.headerContextMenu.showAtPos(_.pageX, _.pageY);
				this.headerContextMenu.fire("Open", $)
			}
		} else if(this[Wiwj]) {
			this[Wiwj].fire("BeforeOpen", $);
			if($.cancel == true) return;
			this[Wiwj].fire("opening", $);
			if($.cancel == true) return;
			this[Wiwj].showAtPos(_.pageX, _.pageY);
			this[Wiwj].fire("Open", $)
		}
		return false
	},
	headerContextMenu: null,
	setHeaderContextMenu: function($) {
		var _ = this._getContextMenu($);
		if(!_) return;
		if(this.headerContextMenu !== _) {
			this.headerContextMenu = _;
			this.headerContextMenu.owner = this;
			$v9(this.el, "contextmenu", this.__OnHtmlContextMenu, this)
		}
	},
	getHeaderContextMenu: function() {
		return this.headerContextMenu
	},
	onRowDblClick: function(_, $) {
		this.on("rowdblclick", _, $)
	},
	onRowClick: function(_, $) {
		this.on("rowclick", _, $)
	},
	onRowMouseDown: function(_, $) {
		this.on("rowmousedown", _, $)
	},
	onRowContextMenu: function(_, $) {
		this.on("rowcontextmenu", _, $)
	},
	onCellClick: function(_, $) {
		this.on("cellclick", _, $)
	},
	onCellMouseDown: function(_, $) {
		this.on("cellmousedown", _, $)
	},
	onCellContextMenu: function(_, $) {
		this.on("cellcontextmenu", _, $)
	},
	onBeforeLoad: function(_, $) {
		this.on("beforeload", _, $)
	},
	onLoad: function(_, $) {
		this.on("load", _, $)
	},
	onLoadError: function(_, $) {
		this.on("loaderror", _, $)
	},
	onPreLoad: function(_, $) {
		this.on("preload", _, $)
	},
	onDrawCell: function(_, $) {
		this.on("drawcell", _, $)
	},
	onCellBeginEdit: function(_, $) {
		this.on("cellbeginedit", _, $)
	},
	getAttrs: function(el) {
		var attrs = _kJP[GR_][$gN][GkN](this, el),
			cs = mini[OIAh](el);
		for(var i = 0, l = cs.length; i < l; i++) {
			var node = cs[i],
				property = jQuery(node).attr("property");
			if(!property) continue;
			property = property.toLowerCase();
			if(property == "columns") attrs.columns = mini._ParseColumns(node);
			else if(property == "data") attrs.data = node.innerHTML
		}
		mini[ENl](el, attrs, ["url", "sizeList", "bodyCls", "bodyStyle", "footerCls", "footerStyle", "pagerCls", "pagerStyle", "onrowdblclick", "onrowclick", "onrowmousedown", "onrowcontextmenu", "oncellclick", "oncellmousedown", "oncellcontextmenu", "onbeforeload", "onpreload", "onloaderror", "onload", "ondrawcell", "oncellbeginedit", "onselectionchanged", "onshowrowdetail", "onhiderowdetail", "idField", "valueField", "ajaxMethod", "ondrawgroup", "pager", "oncellcommitedit", "oncellendedit", "headerContextMenu", "loadingMsg"]);
		mini[XD9s](el, attrs, ["showHeader", "showFooter", "showTop", "allowSortColumn", "allowMoveColumn", "allowResizeColumn", "showHGridLines", "showVGridLines", "showFilterRow", "showSummaryRow", "showFooter", "showTop", "fitColumns", "showLoading", "multiSelect", "allowAlternating", "resultAsData", "allowRowSelect", "enableHotTrack", "showPageIndex", "showPageSize", "showTotalCount", "checkSelectOnLoad", "allowResize", "autoLoad", "autoHideRowDetail", "allowCellSelect", "allowCellEdit", "allowCellWrap", "selectOnLoad"]);
		mini[GGt](el, attrs, ["columnWidth", "frozenStartColumn", "frozenEndColumn", "pageIndex", "pageSize"]);
		if(typeof attrs[$F_V] == "string") attrs[$F_V] = eval(attrs[$F_V]);
		if(!attrs[OzAi] && attrs[B$Gk]) attrs[OzAi] = attrs[B$Gk];
		return attrs
	}
});
PC7(_kJP, "datagrid");
mini._GridSort = function($) {
	this.grid = $;
	$v9($.NL, "mousemove", this.__OnGridHeaderMouseMove, this);
	$v9($.NL, "mouseout", this.__OnGridHeaderMouseOut, this)
};
mini._GridSort[LMj] = {
	__OnGridHeaderMouseOut: function($) {
		if(this._jColumnEl) HMT(this._jColumnEl, "mini-grid-headerCell-hover")
	},
	__OnGridHeaderMouseMove: function(_) {
		var $ = ZW(_.target, "mini-grid-headerCell");
		if($) {
			V7($, "mini-grid-headerCell-hover");
			this._jColumnEl = $
		}
	},
	__onGridHeaderCellClick: function(B) {
		var $ = this.grid,
			A = ZW(B.target, "mini-grid-headerCell");
		if(A) {
			var _ = $[ZUT$](A.id.split("$")[2]);
			if($[J_9] && _ && _.allowDrag) {
				this.dragColumn = _;
				this._columnEl = A;
				this.getDrag().start(B)
			}
		}
	}
};
TsR5 = function($) {
	this.grid = $;
	$v9(this.grid.el, "mousedown", this.NCT, this);
	$.on("refreshHeader", this.P9s, this);
	$.on("layout", this.P9s, this)
};
TsR5[LMj] = {
	P9s: function(A) {
		if(this.splittersEl) mini[BnX](this.splittersEl);
		if(this.splitterTimer) return;
		var $ = this.grid;
		if($[WH4]() == false) return;
		var _ = this;
		this.splitterTimer = setTimeout(function() {
			var H = $[Mh5i](),
				I = H.length,
				E = Y3L($.NL, true),
				B = $.getScrollLeft(),
				G = [];
			for(var J = 0, F = H.length; J < F; J++) {
				var D = H[J],
					C = $[Y_m](D);
				if(!C) break;
				var A = C.top - E.top,
					L = C.right - E.left - 2,
					K = C.height;
				if($[$hR]()) {
					if(J >= $[LdGz]);
				} else L += B;
				var M = $[U1O](D);
				if(M && M.columns) if(M.columns[M.columns.length - 1] == D) if(K + 5 < E.height) {
					A = 0;
					K = E.height
				}
				if($[Q3Gi] && D[JkX]) G[G.length] = "<div id=\"" + D._id + "\" class=\"mini-grid-splitter\" style=\"left:" + L + "px;top:" + A + "px;height:" + K + "px;\"></div>"
			}
			var N = G.join("");
			_.splittersEl = document.createElement("div");
			_.splittersEl.className = "mini-grid-splitters";
			_.splittersEl.innerHTML = N;
			$.NL.appendChild(_.splittersEl);
			_.splitterTimer = null
		}, 100)
	},
	NCT: function(B) {
		var $ = this.grid,
			A = B.target;
		if(XPZ(A, "mini-grid-splitter")) {
			var _ = $.HvFJ[A.id];
			if($[Q3Gi] && _ && _[JkX]) {
				this.splitterColumn = _;
				this.getDrag().start(B)
			}
		}
	},
	getDrag: function() {
		if(!this.drag) this.drag = new mini.Drag({
			capture: true,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this.drag
	},
	Jb: function(_) {
		var $ = this.grid,
			B = $[Y_m](this.splitterColumn);
		this.columnBox = B;
		this.JUge = mini.append(document.body, "<div class=\"mini-grid-proxy\"></div>");
		var A = $.getBox(true);
		A.x = B.x;
		A.width = B.width;
		A.right = B.right;
		$vA(this.JUge, A)
	},
	Pc4: function(A) {
		var $ = this.grid,
			B = mini.copyTo({}, this.columnBox),
			_ = B.width + (A.now[0] - A.init[0]);
		if(_ < $.columnMinWidth) _ = $.columnMinWidth;
		if(_ > $.columnMaxWidth) _ = $.columnMaxWidth;
		Sbkj(this.JUge, _)
	},
	IzO: function(B) {
		var $ = this.grid,
			C = Y3L(this.JUge),
			A = this,
			_ = $[G1s];
		$[G1s] = false;
		setTimeout(function() {
			jQuery(A.JUge).remove();
			A.JUge = null;
			$[G1s] = _
		}, 10);
		$[R3e](this.splitterColumn, C.width)
	}
};
JVK = function($) {
	this.grid = $;
	$v9(this.grid.el, "mousedown", this.NCT, this)
};
JVK[LMj] = {
	NCT: function(B) {
		var $ = this.grid;
		if($[REM]()) return;
		if(XPZ(B.target, "mini-grid-splitter")) return;
		if(B.button == mini.MouseButton.Right) return;
		var A = ZW(B.target, "mini-grid-headerCell");
		if(A) {
			var _ = $.N88(B);
			if($[J_9] && _ && _.allowMove) {
				this.dragColumn = _;
				this._columnEl = A;
				this.getDrag().start(B)
			}
		}
	},
	getDrag: function() {
		if(!this.drag) this.drag = new mini.Drag({
			capture: isIE9 ? false : true,
			onStart: mini.createDelegate(this.Jb, this),
			onMove: mini.createDelegate(this.Pc4, this),
			onStop: mini.createDelegate(this.IzO, this)
		});
		return this.drag
	},
	Jb: function(_) {
		function A(_) {
			var A = _.header;
			if(typeof A == "function") A = A[GkN]($, _);
			if(mini.isNull(A) || A === "") A = "&nbsp;";
			return A
		}
		var $ = this.grid;
		this.JUge = mini.append(document.body, "<div class=\"mini-grid-columnproxy\"></div>");
		this.JUge.innerHTML = "<div class=\"mini-grid-columnproxy-inner\" style=\"height:26px;\">" + A(this.dragColumn) + "</div>";
		mini[Wib](this.JUge, _.now[0] + 15, _.now[1] + 18);
		V7(this.JUge, "mini-grid-no");
		this.moveTop = mini.append(document.body, "<div class=\"mini-grid-movetop\"></div>");
		this.moveBottom = mini.append(document.body, "<div class=\"mini-grid-movebottom\"></div>")
	},
	Pc4: function(A) {
		var $ = this.grid,
			G = A.now[0];
		mini[Wib](this.JUge, G + 15, A.now[1] + 18);
		this.targetColumn = this.insertAction = null;
		var D = ZW(A.event.target, "mini-grid-headerCell");
		if(D) {
			var C = $.N88(A.event);
			if(C && C != this.dragColumn) {
				var _ = $[U1O](this.dragColumn),
					E = $[U1O](C);
				if(_ == E) {
					this.targetColumn = C;
					this.insertAction = "before";
					var F = $[Y_m](this.targetColumn);
					if(G > F.x + F.width / 2) this.insertAction = "after"
				}
			}
		}
		if(this.targetColumn) {
			V7(this.JUge, "mini-grid-ok");
			HMT(this.JUge, "mini-grid-no");
			var B = $[Y_m](this.targetColumn);
			this.moveTop.style.display = "block";
			this.moveBottom.style.display = "block";
			if(this.insertAction == "before") {
				mini[Wib](this.moveTop, B.x - 4, B.y - 9);
				mini[Wib](this.moveBottom, B.x - 4, B.bottom)
			} else {
				mini[Wib](this.moveTop, B.right - 4, B.y - 9);
				mini[Wib](this.moveBottom, B.right - 4, B.bottom)
			}
		} else {
			HMT(this.JUge, "mini-grid-ok");
			V7(this.JUge, "mini-grid-no");
			this.moveTop.style.display = "none";
			this.moveBottom.style.display = "none"
		}
	},
	IzO: function(_) {
		var $ = this.grid;
		mini[BnX](this.JUge);
		mini[BnX](this.moveTop);
		mini[BnX](this.moveBottom);
		$[Y5](this.dragColumn, this.targetColumn, this.insertAction);
		this.JUge = this.moveTop = this.moveBottom = this.dragColumn = this.targetColumn = null
	}
};
WvN = function($) {
	this.grid = $;
	this.grid.on("cellmousedown", this.M5m, this);
	this.grid.on("cellclick", this._No, this);
	$v9(this.grid.el, "keydown", this.Lhb$, this)
};
WvN[LMj] = {
	Lhb$: function(G) {
		var $ = this.grid;
		if(TWAc($.Gnmr, G.target) || TWAc($.NsTj, G.target) || TWAc($.Yj, G.target)) return;
		var A = $.getCurrentCell();
		if(G.shiftKey || G.ctrlKey) return;
		if(G.keyCode == 37 || G.keyCode == 38 || G.keyCode == 39 || G.keyCode == 40) G.preventDefault();
		var C = $[LzT](),
			B = A ? A[1] : null,
			_ = A ? A[0] : null;
		if(!A) _ = $.getCurrent();
		var F = C.indexOf(B),
			D = $.indexOf(_),
			E = $.getData().length;
		switch(G.keyCode) {
		case 27:
			break;
		case 13:
			if($[SjLH] && A) $[M3A1]();
			break;
		case 37:
			if(B) {
				if(F > 0) F -= 1
			} else F = 0;
			break;
		case 38:
			if(_) {
				if(D > 0) D -= 1
			} else D = 0;
			break;
		case 39:
			if(B) {
				if(F < C.length - 1) F += 1
			} else F = 0;
			break;
		case 40:
			if(_) {
				if(D < E - 1) D += 1
			} else D = 0;
			break;
		default:
			break
		}
		B = C[F];
		_ = $.getAt(D);
		if(B && _ && $[SR]) {
			A = [_, B];
			$[NanW](A)
		}
		if(_ && $[R4v]) {
			$[KO2]();
			$[QGK](_)
		}
	},
	_No: function(A) {
		var $ = A.record,
			_ = A.column;
		if(!_[Egr] && !this.grid[RBE]()) if(A.htmlEvent.shiftKey || A.htmlEvent.ctrlKey);
		else this.grid[M3A1]()
	},
	M5m: function(C) {
		var _ = C.record,
			B = C.column,
			$ = this.grid;
		if(this.grid[SR]) {
			var A = [_, B];
			this.grid[NanW](A)
		}
		if($[R4v]) if($[V0]) {
			this.grid.el.onselectstart = function() {};
			if(C.htmlEvent.shiftKey) {
				this.grid.el.onselectstart = function() {
					return false
				};
				C.htmlEvent.preventDefault();
				if(!this.currentRecord) {
					this.grid[M$](_);
					this.currentRecord = this.grid[JFP]()
				} else {
					this.grid[KO2]();
					this.grid.selectRange(this.currentRecord, _)
				}
			} else {
				this.grid.el.onselectstart = function() {};
				if(C.htmlEvent.ctrlKey) {
					this.grid.el.onselectstart = function() {
						return false
					};
					C.htmlEvent.preventDefault()
				}
				if(C.column._multiRowSelect === true || C.htmlEvent.ctrlKey) {
					if($[Jm](_)) $[R4rm](_);
					else $[M$](_)
				} else if($[Jm](_));
				else {
					$[KO2]();
					$[M$](_)
				}
				this.currentRecord = this.grid[JFP]()
			}
		} else if(!$[Jm](_)) {
			$[KO2]();
			$[M$](_)
		} else if(C.htmlEvent.ctrlKey) $[KO2]()
	}
};
mini._GridCellToolTip = function($) {
	this.grid = $;
	this.grid.on("cellmousemove", this.__onGridCellMouseMove, this)
};
mini._GridCellToolTip[LMj] = {
	__onGridCellMouseMove: function(A) {
		var $ = this.grid,
			_ = $._jX(A.record, A.column);
		if(_) if(_.scrollWidth > _.clientWidth) _.title = _.innerText || _.textContent || "";
		else _.title = ""
	}
};
mini.GridEditor = function() {
	this._inited = true;
	FIa[GR_][KNT][GkN](this);
	this[Sir]();
	this.el.uid = this.uid;
	this[S1B]();
	this.RH();
	this[QlR](this.uiCls)
};
Iov(mini.GridEditor, FIa, {
	el: null,
	_create: function() {
		this.el = document.createElement("input");
		this.el.type = "text";
		this.el.style.width = "100%"
	},
	getValue: function() {
		return this.el.value
	},
	setValue: function($) {
		this.el.value = $
	},
	setWidth: function($) {}
});
I1B = function() {
	I1B[GR_][KNT][GkN](this)
};
Iov(I1B, FIa, {
	pageIndex: 0,
	pageSize: 10,
	totalCount: 0,
	totalPage: 0,
	showPageIndex: true,
	showPageSize: true,
	showTotalCount: true,
	showPageInfo: true,
	_clearBorder: false,
	showButtonText: false,
	showButtonIcon: true,
	firstText: "\u9996\u9875",
	prevText: "\u4e0a\u4e00\u9875",
	nextText: "\u4e0b\u4e00\u9875",
	lastText: "\u5c3e\u9875",
	pageInfoText: "\u6bcf\u9875 {0} \u6761,\u5171 {1} \u6761",
	sizeList: [10, 20, 50, 100],
	uiCls: "mini-pager",
	_create: function() {
		this.el = document.createElement("div");
		this.el.className = "mini-pager";
		var $ = "<div class=\"mini-pager-left\"></div><div class=\"mini-pager-right\"></div>";
		this.el.innerHTML = $;
		this.buttonsEl = this._leftEl = this.el.childNodes[0];
		this._rightEl = this.el.childNodes[1];
		this.sizeEl = mini.append(this.buttonsEl, "<span class=\"mini-pager-size\"></span>");
		this.sizeCombo = new ZjG();
		this.sizeCombo.setName("pagesize");
		this.sizeCombo[_wJ](45);
		this.sizeCombo[Ivp](this.sizeEl);
		mini.append(this.sizeEl, "<span class=\"separator\"></span>");
		this.firstButton = new Has6();
		this.firstButton[Ivp](this.buttonsEl);
		this.prevButton = new Has6();
		this.prevButton[Ivp](this.buttonsEl);
		this.indexEl = document.createElement("span");
		this.indexEl.className = "mini-pager-index";
		this.indexEl.innerHTML = "<input id=\"\" type=\"text\" class=\"mini-pager-num\"/><span class=\"mini-pager-pages\">/ 0</span>";
		this.buttonsEl.appendChild(this.indexEl);
		this.numInput = this.indexEl.firstChild;
		this.pagesLabel = this.indexEl.lastChild;
		this.nextButton = new Has6();
		this.nextButton[Ivp](this.buttonsEl);
		this.lastButton = new Has6();
		this.lastButton[Ivp](this.buttonsEl);
		this.firstButton.setPlain(true);
		this.prevButton.setPlain(true);
		this.nextButton.setPlain(true);
		this.lastButton.setPlain(true);
		this.update()
	},
	destroy: function($) {
		if(this.pageSelect) {
			mini[J9](this.pageSelect);
			this.pageSelect = null
		}
		if(this.numInput) {
			mini[J9](this.numInput);
			this.numInput = null
		}
		this.sizeEl = null;
		this.buttonsEl = null;
		I1B[GR_][EqU5][GkN](this, $)
	},
	_initEvents: function() {
		I1B[GR_][S1B][GkN](this);
		this.firstButton.on("click", function($) {
			this.XhM(0)
		}, this);
		this.prevButton.on("click", function($) {
			this.XhM(this[JAYp] - 1)
		}, this);
		this.nextButton.on("click", function($) {
			this.XhM(this[JAYp] + 1)
		}, this);
		this.lastButton.on("click", function($) {
			this.XhM(this.totalPage)
		}, this);

		function $() {
			var $ = parseInt(this.numInput.value);
			if(isNaN($)) this.update();
			else this.XhM($ - 1)
		}
		$v9(this.numInput, "change", function(_) {
			$[GkN](this)
		}, this);
		$v9(this.numInput, "keydown", function(_) {
			if(_.keyCode == 13) {
				$[GkN](this);
				_.stopPropagation()
			}
		}, this);
		this.sizeCombo.on("valuechanged", this.Tq5, this)
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		mini.layout(this._leftEl);
		mini.layout(this._rightEl)
	},
	setPageIndex: function($) {
		if(isNaN($)) return;
		this[JAYp] = $;
		this.update()
	},
	getPageIndex: function() {
		return this[JAYp]
	},
	setPageSize: function($) {
		if(isNaN($)) return;
		this[FX_r] = $;
		this.update()
	},
	getPageSize: function() {
		return this[FX_r]
	},
	setTotalCount: function($) {
		$ = parseInt($);
		if(isNaN($)) return;
		this[Ak] = $;
		this.update()
	},
	getTotalCount: function() {
		return this[Ak]
	},
	setSizeList: function($) {
		if(!mini.isArray($)) return;
		this[$F_V] = $;
		this.update()
	},
	getSizeList: function() {
		return this[$F_V]
	},
	setShowPageSize: function($) {
		this.showPageSize = $;
		this.update()
	},
	getShowPageSize: function() {
		return this.showPageSize
	},
	setShowPageIndex: function($) {
		this.showPageIndex = $;
		this.update()
	},
	getShowPageIndex: function() {
		return this.showPageIndex
	},
	setShowTotalCount: function($) {
		this.showTotalCount = $;
		this.update()
	},
	getShowTotalCount: function() {
		return this.showTotalCount
	},
	setShowPageInfo: function($) {
		this.showPageInfo = $;
		this.update()
	},
	getShowPageInfo: function() {
		return this.showPageInfo
	},
	getTotalPage: function() {
		return this.totalPage
	},
	update: function($, H, F) {
		if(mini.isNumber($)) this[JAYp] = parseInt($);
		if(mini.isNumber(H)) this[FX_r] = parseInt(H);
		if(mini.isNumber(F)) this[Ak] = parseInt(F);
		this.totalPage = parseInt(this[Ak] / this[FX_r]) + 1;
		if((this.totalPage - 1) * this[FX_r] == this[Ak]) this.totalPage -= 1;
		if(this[Ak] == 0) this.totalPage = 0;
		if(this[JAYp] > this.totalPage - 1) this[JAYp] = this.totalPage - 1;
		if(this[JAYp] <= 0) this[JAYp] = 0;
		if(this.totalPage <= 0) this.totalPage = 0;
		this.firstButton.enable();
		this.prevButton.enable();
		this.nextButton.enable();
		this.lastButton.enable();
		if(this[JAYp] == 0) {
			this.firstButton.disable();
			this.prevButton.disable()
		}
		if(this[JAYp] >= this.totalPage - 1) {
			this.nextButton.disable();
			this.lastButton.disable()
		}
		this.numInput.value = this[JAYp] > -1 ? this[JAYp] + 1 : 0;
		this.pagesLabel.innerHTML = "/ " + this.totalPage;
		var K = this[$F_V].clone();
		if(K.indexOf(this[FX_r]) == -1) {
			K.push(this[FX_r]);
			K = K.sort(function($, _) {
				return $ > _
			})
		}
		var _ = [];
		for(var E = 0, B = K.length; E < B; E++) {
			var D = K[E],
				G = {};
			G.text = D;
			G.id = D;
			_.push(G)
		}
		this.sizeCombo[OUQ](_);
		this.sizeCombo[UD7](this[FX_r]);
		var A = this.firstText,
			J = this.prevText,
			C = this.nextText,
			I = this.lastText;
		if(this.showButtonText == false) A = J = C = I = "";
		this.firstButton[Chg](A);
		this.prevButton[Chg](J);
		this.nextButton[Chg](C);
		this.lastButton[Chg](I);
		A = this.firstText, J = this.prevText, C = this.nextText, I = this.lastText;
		if(this.showButtonText == true) A = J = C = I = "";
		this.firstButton.setTooltip(A);
		this.prevButton.setTooltip(J);
		this.nextButton.setTooltip(C);
		this.lastButton.setTooltip(I);
		this.firstButton.setIconCls(this.showButtonIcon ? "mini-pager-first" : "");
		this.prevButton.setIconCls(this.showButtonIcon ? "mini-pager-prev" : "");
		this.nextButton.setIconCls(this.showButtonIcon ? "mini-pager-next" : "");
		this.lastButton.setIconCls(this.showButtonIcon ? "mini-pager-last" : "");
		this._rightEl.innerHTML = String.format(this.pageInfoText, this.pageSize, this[Ak]);
		this.indexEl.style.display = this.showPageIndex ? "" : "none";
		this.sizeEl.style.display = this.showPageSize ? "" : "none";
		this._rightEl.style.display = this.showPageInfo ? "" : "none"
	},
	Tq5: function(_) {
		var $ = parseInt(this.sizeCombo.getValue());
		this.XhM(0, $)
	},
	XhM: function($, _) {
		var A = {
			pageIndex: mini.isNumber($) ? $ : this.pageIndex,
			pageSize: mini.isNumber(_) ? _ : this.pageSize,
			cancel: false
		};
		if(A[JAYp] > this.totalPage - 1) A[JAYp] = this.totalPage - 1;
		if(A[JAYp] < 0) A[JAYp] = 0;
		this.fire("pagechanged", A);
		if(A.cancel == false) this.update(A.pageIndex, A[FX_r])
	},
	onPageChanged: function(_, $) {
		this.on("pagechanged", _, $)
	},
	getAttrs: function(el) {
		var attrs = I1B[GR_][$gN][GkN](this, el);
		mini[ENl](el, attrs, ["onpagechanged", "sizeList"]);
		mini[XD9s](el, attrs, ["showPageIndex", "showPageSize", "showTotalCount", "showPageInfo"]);
		mini[GGt](el, attrs, ["pageIndex", "pageSize", "totalCount"]);
		if(typeof attrs[$F_V] == "string") attrs[$F_V] = eval(attrs[$F_V]);
		return attrs
	}
});
PC7(I1B, "pager");
Bpz8 = function() {
	this.columns = [];
	Bpz8[GR_][KNT][GkN](this)
};
Iov(Bpz8, L2, {
	width: 300,
	height: 180,
	treeColumn: "",
	columns: [],
	columnWidth: 80,
	UuJM: true,
	Dqn: "mini-treegrid-border",
	OUH: "mini-treegrid-header",
	R7: "mini-treegrid-body",
	XMk: "mini-treegrid-node",
	GAj: "mini-treegrid-nodes",
	I9W: "mini-treegrid-selectedNode",
	Oz: "mini-treegrid-hoverNode",
	BI: "mini-treegrid-expand",
	Bd: "mini-treegrid-collapse",
	OH4l: "mini-treegrid-ec-icon",
	CW: "mini-treegrid-nodeTitle",
	_FU: function(_) {
		if(!_) return null;
		var $ = this.MFL(_);
		return $
	},
	uiCls: "mini-treegrid",
	_create: function() {
		Bpz8[GR_][Sir][GkN](this);
		$v9(this.H23I, "scroll", this.Zmc, this)
	},
	Iu0b: function($) {
		return this.uid + "$column$" + $.id
	},
	LLWI: function(D) {
		var F = "",
			B = this[Mh5i]();
		if(isIE) {
			if(isIE6 || isIE7 || (isIE8 && !jQuery.boxModel) || (isIE9 && !jQuery.boxModel)) F += "<tr style=\"display:none;\">";
			else F += "<tr >"
		} else F += "<tr>";
		for(var $ = 0, C = B.length; $ < C; $++) {
			var A = B[$],
				_ = A.width,
				E = this.Iu0b(A) + "$" + D;
			F += "<td id=\"" + E + "\" style=\"padding:0;border:0;margin:0;height:0;";
			if(A.width) F += "width:" + A.width;
			F += "\" ></td>"
		}
		F += "</tr>";
		return F
	},
	Fy: function() {
		var D = this[Mh5i](),
			E = [];
		E[E.length] = "<div class=\"mini-treegrid-headerInner\"><table class=\"mini-treegrid-table\" cellspacing=\"0\" cellpadding=\"0\">";
		E[E.length] = this.LLWI();
		E[E.length] = "<tr>";
		for(var C = 0, $ = D.length; C < $; C++) {
			var A = D[C],
				B = A.header;
			if(typeof B == "function") B = B[GkN](this, A);
			if(mini.isNull(B) || B === "") B = "&nbsp;";
			var _ = A.width;
			if(mini.isNumber(_)) _ = _ + "px";
			E[E.length] = "<td class=\"";
			if(A.headerCls) E[E.length] = A.headerCls;
			E[E.length] = "\" style=\"";
			if(A.headerStyle) E[E.length] = A.headerStyle + ";";
			if(_) E[E.length] = "width:" + _ + ";";
			if(A.headerAlign) E[E.length] = "text-align:" + A.headerAlign + ";";
			E[E.length] = "\">";
			E[E.length] = B;
			E[E.length] = "</td>"
		}
		E[E.length] = "</tr></table></div>";
		this.NL.innerHTML = E.join("")
	},
	T7$: function(B, L, F) {
		var J = !F;
		if(!F) F = [];
		var G = B[this.textField];
		if(G === null || G === undefined) G = "";
		var H = this.isLeaf(B),
			$ = this.getLevel(B),
			D = "";
		if(!H) D = this.isExpandedNode(B) ? this.BI : this.Bd;
		var E = this[Mh5i]();
		F[F.length] = "<table class=\"mini-treegrid-nodeTitle ";
		F[F.length] = D;
		F[F.length] = "\" cellspacing=\"0\" cellpadding=\"0\">";
		F[F.length] = this.LLWI();
		F[F.length] = "<tr>";
		for(var I = 0, _ = E.length; I < _; I++) {
			var C = E[I],
				K = this.GgLg(B, C),
				A = C.width;
			if(typeof A == "number") A = A + "px";
			F[F.length] = "<td class=\"";
			if(K.cellCls) F[F.length] = K.cellCls;
			F[F.length] = "\" style=\"";
			if(K.cellStyle) {
				F[F.length] = K.cellStyle;
				F[F.length] = ";"
			}
			if(C.align) {
				F[F.length] = "text-align:";
				F[F.length] = C.align;
				F[F.length] = ";"
			}
			F[F.length] = "\">";
			F[F.length] = K.cellHtml;
			F[F.length] = "</td>";
			if(K.rowCls) rowCls = K.rowCls;
			if(K.rowStyle) rowStyle = K.rowStyle
		}
		F[F.length] = "</table>";
		if(J) return F.join("")
	},
	doUpdate: function() {
		if(!this.Y5o) return;
		this.Fy();
		var $ = new Date(),
			_ = this._getViewChildNodes(this.root),
			B = [];
		this.Xd(_, this.root, B);
		var A = B.join("");
		this.H23I.innerHTML = A;
		this.WU$Y()
	},
	doLayout: function() {
		if(!this.canLayout()) return;
		var C = this[_H](),
			D = this[J4j](),
			_ = this[Y1Q](true),
			A = this[DTTy](true),
			B = this[Ykh2](),
			$ = A - B;
		this.H23I.style.width = _ + "px";
		this.H23I.style.height = $ + "px";
		this.RSm()
	},
	RSm: function() {
		var A = this[Y1Q](true);
		if(isIE) {
			var _ = this.NL.firstChild.firstChild,
				C = this.H23I.firstChild;
			if(this.H23I.offsetHeight >= this.H23I.scrollHeight) {
				if(C) C.style.width = "100%";
				if(_) _.style.width = "100%"
			} else {
				if(C) {
					var $ = parseInt(C.parentNode.offsetWidth - 17) + "px";
					C.style.width = $
				}
				if(_) _.style.width = $
			}
		}
		if(this.H23I.offsetHeight < this.H23I.scrollHeight) this.NL.firstChild.style.width = (A - 17) + "px";
		else this.NL.firstChild.style.width = "100%";
		try {
			$ = this.NL.firstChild.firstChild.offsetWidth;
			this.H23I.firstChild.style.width = $ + "px"
		} catch(B) {}
	},
	getHeaderHeight: function() {
		return KwY(this.NL)
	},
	GgLg: function($, B) {
		var D = this[HbI];
		if(D && this.hasChildren($)) D = this[F0q];
		var _ = $[B.field],
			C = {
				isLeaf: this.isLeaf($),
				rowIndex: this.indexOf($),
				showCheckBox: D,
				iconCls: this.getNodeIcon($),
				showTreeIcon: this.showTreeIcon,
				sender: this,
				record: $,
				row: $,
				node: $,
				column: B,
				field: B ? B.field : null,
				value: _,
				cellHtml: _,
				rowCls: null,
				cellCls: B ? (B.cellCls || "") : "",
				rowStyle: null,
				cellStyle: B ? (B.cellStyle || "") : ""
			};
		if(B.dateFormat) if(mini.isDate(C.value)) C.cellHtml = mini.formatDate(_, B.dateFormat);
		else C.cellHtml = _;
		var A = B.renderer;
		if(A) {
			fn = typeof A == "function" ? A : window[A];
			if(fn) C.cellHtml = fn[GkN](B, C)
		}
		this.fire("drawcell", C);
		if(C.cellHtml === null || C.cellHtml === undefined || C.cellHtml === "") C.cellHtml = "&nbsp;";
		if(!this.treeColumn || this.treeColumn !== B.name) return C;
		this.Zfp(C);
		return C
	},
	Zfp: function(G) {
		var A = G.node;
		if(mini.isNull(G[OvNg])) G[OvNg] = this[OvNg];
		var F = G.cellHtml,
			B = this.isLeaf(A),
			$ = this.getLevel(A) * 18,
			C = "";
		if(G.cellCls) G.cellCls += " mini-treegrid-treecolumn ";
		else G.cellCls = " mini-treegrid-treecolumn ";
		var E = "<div class=\"mini-treegrid-treecolumn-inner " + C + "\">";
		if(!B) E += "<a href=\"#\" onclick=\"return false;\"  hidefocus class=\"" + this.OH4l + "\" style=\"left:" + ($) + "px;\"></a>";
		$ += 18;
		if(G[OvNg]) {
			var _ = this.getNodeIcon(A);
			E += "<div class=\"" + _ + " mini-treegrid-nodeicon\" style=\"left:" + $ + "px;\"></div>";
			$ += 18
		}
		F = "<span class=\"mini-tree-nodetext\">" + F + "</span>";
		if(G[HbI]) {
			var D = this.F1y(A);
			F = "<input type=\"checkbox\" id=\"" + D + "\" class=\"" + this.Hm + "\" hidefocus />" + F
		}
		E += "<div class=\"mini-treegrid-nodeshow\" style=\"margin-left:" + ($ + 2) + "px;\">" + F + "</div>";
		E += "</div>";
		F = E;
		G.cellHtml = F
	},
	setColumns: function(_) {
		if(!mini.isArray(_)) _ = [];
		this.columns = _;
		for(var $ = 0, D = this.columns.length; $ < D; $++) {
			var B = this.columns[$];
			if(B.type) {
				if(!mini.isNull(B.header) && typeof B.header !== "function") if(B.header.trim() == "") delete B.header;
				var C = mini[S56](B.type);
				if(C) {
					var E = mini.copyTo({}, B);
					mini.copyTo(B, C);
					mini.copyTo(B, E)
				}
			}
			if(typeof B.init == "function") {
				B.init(this);
				delete B.init
			}
			var A = parseInt(B.width);
			if(mini.isNumber(A) && String(A) == B.width) B.width = A + "px";
			if(mini.isNull(B.width)) B.width = this[DzN] + "px";
			if(B.type == "checkcolumn") throw new Error("treegrid not suport checkcolumn")
		}
		this[Mdk]()
	},
	getColumns: function() {
		return this.columns
	},
	getBottomColumns: function() {
		return this.columns
	},
	setTreeColumn: function($) {
		if(this.treeColumn != $) {
			this.treeColumn = $;
			this[Mdk]()
		}
	},
	getTreeColumn: function($) {
		return this.treeColumn
	},
	Zmc: function(_) {
		var $ = this.H23I.scrollLeft;
		this.NL.firstChild.scrollLeft = $
	},
	getAttrs: function(_) {
		var E = Bpz8[GR_][$gN][GkN](this, _);
		mini[ENl](_, E, ["treeColumn", "ondrawcell"]);
		var C = mini[OIAh](_);
		for(var $ = 0, D = C.length; $ < D; $++) {
			var B = C[$],
				A = jQuery(B).attr("property");
			if(!A) continue;
			A = A.toLowerCase();
			if(A == "columns") E.columns = mini._ParseColumns(B)
		}
		delete E.data;
		return E
	}
});
PC7(Bpz8, "treegrid");
mini.RadioButtonList = NjUP, mini.ValidatorBase = CLi, mini.AutoComplete = FGFZ, mini.CheckBoxList = JcT, mini.DataBinding = U1zK, mini.OutlookTree = Eke, mini.OutlookMenu = UeH$, mini.TextBoxList = R8ty, mini.TimeSpinner = Ukz, mini.ListControl = GR, mini.OutlookBar = QF30, mini.FileUpload = Yiw9, mini.TreeSelect = Zt0, mini.DatePicker = KG, mini.ButtonEdit = OuY, mini.PopupEdit = AU6v, mini.Component = SgZ, mini.TreeGrid = Bpz8, mini.DataGrid = _kJP, mini.MenuItem = Oy2, mini.Splitter = XQ, mini.HtmlFile = KHy, mini.Calendar = _8$e, mini.ComboBox = ZjG, mini.TextArea = Q47, mini.Password = WF0, mini.CheckBox = OgF, mini.DataSet = Z4nR, mini.Include = DeC, mini.Spinner = HY84, mini.ListBox = YTBL, mini.TextBox = Pg3R, mini.Control = FIa, mini.Layout = Gi2Z, mini.Window = EzY, mini.Lookup = $nLw, mini.Button = Has6, mini.Hidden = $zd, mini.Pager = I1B, mini.Panel = NpV8, mini.Popup = _s, mini.Tree = L2, mini.Menu = PF8P, mini.Tabs = Ck3, mini.Fit = ECe, mini.Box = W4;
mini.locale = "en-US";
mini.dateInfo = {
	monthsLong: ["\u4e00\u6708", "\u4e8c\u6708", "\u4e09\u6708", "\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708", "\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708", "\u5341\u4e00\u6708", "\u5341\u4e8c\u6708"],
	monthsShort: ["1\u6708", "2\u6708", "3\u6708", "4\u6708", "5\u6708", "6\u6708", "7\u6708", "8\u6708", "9\u6708", "10\u6708", "11\u6708", "12\u6708"],
	daysLong: ["\u661f\u671f\u65e5", "\u661f\u671f\u4e00", "\u661f\u671f\u4e8c", "\u661f\u671f\u4e09", "\u661f\u671f\u56db", "\u661f\u671f\u4e94", "\u661f\u671f\u516d"],
	daysShort: ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"],
	quarterLong: ["\u4e00\u5b63\u5ea6", "\u4e8c\u5b63\u5ea6", "\u4e09\u5b63\u5ea6", "\u56db\u5b63\u5ea6"],
	quarterShort: ["Q1", "Q2", "Q2", "Q4"],
	halfYearLong: ["\u4e0a\u534a\u5e74", "\u4e0b\u534a\u5e74"],
	patterns: {
		"d": "yyyy-M-d",
		"D": "yyyy\u5e74M\u6708d\u65e5",
		"f": "yyyy\u5e74M\u6708d\u65e5 H:mm",
		"F": "yyyy\u5e74M\u6708d\u65e5 H:mm:ss",
		"g": "yyyy-M-d H:mm",
		"G": "yyyy-M-d H:mm:ss",
		"m": "MMMd\u65e5",
		"o": "yyyy-MM-ddTHH:mm:ss.fff",
		"s": "yyyy-MM-ddTHH:mm:ss",
		"t": "H:mm",
		"T": "H:mm:ss",
		"U": "yyyy\u5e74M\u6708d\u65e5 HH:mm:ss",
		"y": "yyyy\u5e74MM\u6708"
	},
	tt: {
		"AM": "\u4e0a\u5348",
		"PM": "\u4e0b\u5348"
	},
	ten: {
		"Early": "\u4e0a\u65ec",
		"Mid": "\u4e2d\u65ec",
		"Late": "\u4e0b\u65ec"
	},
	today: "\u4eca\u5929",
	clockType: 24
};
if(_8$e) mini.copyTo(_8$e.prototype, {
	firstDayOfWeek: 0,
	todayText: "\u4eca\u5929",
	clearText: "\u6e05\u9664",
	okText: "\u786e\u5b9a",
	cancelText: "\u53d6\u6d88",
	daysShort: ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"],
	format: "yyyy\u5e74MM\u6708",
	timeFormat: "H:mm"
});
for(var id in mini) {
	var clazz = mini[id];
	if(clazz && clazz[LMj] && clazz[LMj].isControl) clazz[LMj][Pjk9] = "\u4e0d\u80fd\u4e3a\u7a7a"
}
if(Pg3R) {
	var vtypeErrorTexts = {
		emailErrorText: "\u8bf7\u8f93\u5165\u90ae\u4ef6\u683c\u5f0f",
		urlErrorText: "\u8bf7\u8f93\u5165URL\u683c\u5f0f",
		floatErrorText: "\u8bf7\u8f93\u5165\u6570\u5b57",
		intErrorText: "\u8bf7\u8f93\u5165\u6574\u6570",
		dateErrorText: "\u8bf7\u8f93\u5165\u65e5\u671f\u683c\u5f0f {0}",
		maxLengthErrorText: "\u4e0d\u80fd\u8d85\u8fc7 {0} \u4e2a\u5b57\u7b26",
		minLengthErrorText: "\u4e0d\u80fd\u5c11\u4e8e {0} \u4e2a\u5b57\u7b26",
		maxErrorText: "\u6570\u5b57\u4e0d\u80fd\u5927\u4e8e {0} ",
		minErrorText: "\u6570\u5b57\u4e0d\u80fd\u5c0f\u4e8e {0} ",
		rangeLengthErrorText: "\u5b57\u7b26\u957f\u5ea6\u5fc5\u987b\u5728 {0} \u5230 {1} \u4e4b\u95f4",
		rangeCharErrorText: "\u5b57\u7b26\u6570\u5fc5\u987b\u5728 {0} \u5230 {1} \u4e4b\u95f4",
		rangeErrorText: "\u6570\u5b57\u5fc5\u987b\u5728 {0} \u5230 {1} \u4e4b\u95f4"
	};
	mini.copyTo(Pg3R.prototype, vtypeErrorTexts);
	mini.copyTo(WF0.prototype, vtypeErrorTexts);
	mini.copyTo(Q47.prototype, vtypeErrorTexts)
}
if(I1B) mini.copyTo(I1B.prototype, {
	firstText: "\u9996\u9875",
	prevText: "\u4e0a\u4e00\u9875",
	nextText: "\u4e0b\u4e00\u9875",
	lastText: "\u5c3e\u9875",
	pageInfoText: "\u6bcf\u9875 {0} \u6761,\u5171 {1} \u6761"
});
if(window.mini.Gantt) {
	mini.GanttView.ShortWeeks = ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"];
	mini.GanttView.LongWeeks = ["\u661f\u671f\u65e5", "\u661f\u671f\u4e00", "\u661f\u671f\u4e8c", "\u661f\u671f\u4e09", "\u661f\u671f\u56db", "\u661f\u671f\u4e94", "\u661f\u671f\u516d"];
	mini.Gantt.PredecessorLinkType = [{
		ID: 0,
		Name: "\u5b8c\u6210-\u5b8c\u6210(FF)",
		Short: "FF"
	}, {
		ID: 1,
		Name: "\u5b8c\u6210-\u5f00\u59cb(FS)",
		Short: "FS"
	}, {
		ID: 2,
		Name: "\u5f00\u59cb-\u5b8c\u6210(SF)",
		Short: "SF"
	}, {
		ID: 3,
		Name: "\u5f00\u59cb-\u5f00\u59cb(SS)",
		Short: "SS"
	}];
	mini.Gantt.ConstraintType = [{
		ID: 0,
		Name: "\u8d8a\u65e9\u8d8a\u597d"
	}, {
		ID: 1,
		Name: "\u8d8a\u665a\u8d8a\u597d"
	}, {
		ID: 2,
		Name: "\u5fc5\u987b\u5f00\u59cb\u4e8e"
	}, {
		ID: 3,
		Name: "\u5fc5\u987b\u5b8c\u6210\u4e8e"
	}, {
		ID: 4,
		Name: "\u4e0d\u5f97\u65e9\u4e8e...\u5f00\u59cb"
	}, {
		ID: 5,
		Name: "\u4e0d\u5f97\u665a\u4e8e...\u5f00\u59cb"
	}, {
		ID: 6,
		Name: "\u4e0d\u5f97\u65e9\u4e8e...\u5b8c\u6210"
	}, {
		ID: 7,
		Name: "\u4e0d\u5f97\u665a\u4e8e...\u5b8c\u6210"
	}];
	mini.copyTo(mini.Gantt, {
		ID_Text: "\u6807\u8bc6\u53f7",
		Name_Text: "\u4efb\u52a1\u540d\u79f0",
		PercentComplete_Text: "\u8fdb\u5ea6",
		Duration_Text: "\u5de5\u671f",
		Start_Text: "\u5f00\u59cb\u65e5\u671f",
		Finish_Text: "\u5b8c\u6210\u65e5\u671f",
		Critical_Text: "\u5173\u952e\u4efb\u52a1",
		PredecessorLink_Text: "\u524d\u7f6e\u4efb\u52a1",
		Work_Text: "\u5de5\u65f6",
		Priority_Text: "\u91cd\u8981\u7ea7\u522b",
		Weight_Text: "\u6743\u91cd",
		OutlineNumber_Text: "\u5927\u7eb2\u5b57\u6bb5",
		OutlineLevel_Text: "\u4efb\u52a1\u5c42\u7ea7",
		ActualStart_Text: "\u5b9e\u9645\u5f00\u59cb\u65e5\u671f",
		ActualFinish_Text: "\u5b9e\u9645\u5b8c\u6210\u65e5\u671f",
		WBS_Text: "WBS",
		ConstraintType_Text: "\u9650\u5236\u7c7b\u578b",
		ConstraintDate_Text: "\u9650\u5236\u65e5\u671f",
		Department_Text: "\u90e8\u95e8",
		Principal_Text: "\u8d1f\u8d23\u4eba",
		Assignments_Text: "\u8d44\u6e90\u540d\u79f0",
		Summary_Text: "\u6458\u8981\u4efb\u52a1",
		Task_Text: "\u4efb\u52a1",
		Baseline_Text: "\u6bd4\u8f83\u57fa\u51c6",
		LinkType_Text: "\u94fe\u63a5\u7c7b\u578b",
		LinkLag_Text: "\u5ef6\u9694\u65f6\u95f4",
		From_Text: "\u4ece",
		To_Text: "\u5230",
		Goto_Text: "\u8f6c\u5230\u4efb\u52a1",
		UpGrade_Text: "\u5347\u7ea7",
		DownGrade_Text: "\u964d\u7ea7",
		Add_Text: "\u65b0\u589e",
		Edit_Text: "\u7f16\u8f91",
		Remove_Text: "\u5220\u9664",
		Move_Text: "\u79fb\u52a8",
		ZoomIn_Text: "\u653e\u5927",
		ZoomOut_Text: "\u7f29\u5c0f",
		Deselect_Text: "\u53d6\u6d88\u9009\u62e9",
		Split_Text: "\u62c6\u5206\u4efb\u52a1"
	})
}