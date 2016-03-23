jQuery(document).ready(function() {
	jQuery('ul#menu li').each(function() {
		var currentURI = window.location.href;
		var links = jQuery('a', this);
		for (var i = 0; i < links.size(); i++) {
			var elem = links.eq(i);
			var href = elem.attr('href');
			var hrefLength = href.length;
			var compareTo = currentURI.substr(-1 * hrefLength);
			if (href == compareTo) {
				elem.addClass('selected');
			}
		}
		;
	});

});
