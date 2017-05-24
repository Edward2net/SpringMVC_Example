jQuery(document).ready(function($) {

	$("#search-form").submit(function(event) {

		enableSearchButton(false);

		event.preventDefault();

		searchViaAjax();

	});

});

function searchViaAjax() {
	var search = {}
	search["name"] = $("#name").val();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/Spring/queryAjax",
		data : JSON.stringify(search),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		done : function(e) {
			console.log("DONE");
			enableSearchButton(true);
		}
	});

}

jQuery(document).ready(function($) {

	$("#search-form-x").submit(function(event) {

		enableSearchButton(false);

		event.preventDefault();

		searchViaAjaxX();

	});

});

function searchViaAjaxX() {

	$.ajax({
		type : "POST",
		contentType : "application/x-www-form-urlencoded",
		url : "/Spring/queryAjaxX",
		data : {
			'name' : $("#nameX").val()
		},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {

			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		done : function(e) {
			debugger;
			console.log("DONE");
			enableSearchButton(true);
		}
	});

}

function enableSearchButton(flag) {
	$("#btn-search").prop("disabled", flag);
}

function display(data) {
	var json = "<h4>Ajax Response</h4><pre>" + JSON.stringify(data, null, 4)
			+ "</pre>";
	$('#feedback').html(json);
}

function displayBut(data) {
	var json = "<h4>Ajax Response</h4><pre>" + data + "</pre>";
	$('#feedback').html(json);
}


var isJpg = function(name) {
    return name.match(/jpg$/i)
};
    
var isPng = function(name) {
    return name.match(/png$/i)
};

$(document)
		.ready(
				function() {

					var imgContainer = $('#imgContainer');

					$('#btnUpload')
							.on('click', function() {
								debugger;
										var file = $('#updateFile');
										var filename = $.trim(file.val());
										if (!(isJpg(filename) || isPng(filename))) {
											alert('Please browse a JPG/PNG file to upload ...');
											return;
										}
										$.ajax({url : '/Spring/upload',
												type : "POST",
												data : new FormData(
															document.getElementById("fileUploadForm")),
															enctype : 'multipart/form-data',
															processData : false,
															contentType : false
														})
												.done(
														function(data) {
															displayBut(data);;
														})
												.fail(
														function(jqXHR,
																textStatus) {
															// alert(jqXHR.responseText);
															alert('File upload failed ...');
														});

									});

					$('#btnClear').on('click', function() {
						$('#updateFile').val('');
					});

					$('#btnDownloadFile')
							.on('click',function() {
										debugger;
										var form = document.createElement("form");
										form.method = "post";
										form.action = "/Spring/download/20170519113955apple-touch-icon.png";
										document.body.appendChild(form);
										form.submit();
										document.body.removeChild(form);
									});
					
					$('#btnDownloadFileJqueryFile')
					.on(
							'click',
							function() {
								var alertBut = $('#btnDownloadFileJqueryFile');
								
								debugger;
								var form = document
										.createElement("form");
								form.method = "post";
								form.action = "/Spring/downloadResource";
								document.body.appendChild(form);
								form.submit();
								document.body.removeChild(form);
							});

				});