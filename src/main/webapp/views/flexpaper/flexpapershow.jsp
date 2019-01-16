<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
		<script src="${path }/resources/jquery/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="${path }/resources/flexpaper/flexpaper_flash.js"></script>
	</head>
	<body>
		<div style="position: absolute;">
			<div id="viewerPlaceHolder" style="display: block;"></div>

			<script type="text/javascript">
			var dialog = frameElement.dialog;
			$('#viewerPlaceHolder').width(dialog._width-10);
			$('#viewerPlaceHolder').height(dialog._height-59);
			var fp = new FlexPaperViewer(
				'${path }/resources/flexpaper/FlexPaperViewer',
				'viewerPlaceHolder', {
					config : {
						SwfFile : '${path}${swfFlie}',
						Scale : 1.0,
						ZoomTransition : 'easeOut',
						ZoomTime : 0.5,
						ZoomInterval : 0.2,
						FitPageOnLoad : false,
						FitWidthOnLoad : false,
						FullScreenAsMaxWindow : false,
						ProgressiveLoading : false,
						MinZoomSize : 0.2,
						MaxZoomSize : 5,
						SearchMatchAll : false,
						InitViewMode : 'SinglePage',
	
						ViewModeToolsVisible : true,
						ZoomToolsVisible : true,
						NavToolsVisible : true,
						CursorToolsVisible : true,
						SearchToolsVisible : true,
	
						localeChain : 'zh_CN'
					}
				});
			</script>
		</div>
	</body>
</html>