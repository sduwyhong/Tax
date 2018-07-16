

   $('.button-collapse').sideNav({menuWidth: 240, activationWidth: 70});
 
   $( window ).load(function() {

    if(window.location.hash == '#app') {
        $('.navbar-fixed, .page-footer').hide();
    } 
    var $container = $('.isotope').isotope({
    	layoutMode: 'fitRows'
    });
  });
  
    // Show Modal when leaving site
    var xValue, yValue;
    var isYIncreasing;
    $('html, body').mousemove(function( event ) {
            
            var isYIncreasing = yValue > event.pageY;
            xValue = event.pageX;
            yValue = event.pageY;
            
            if (isYIncreasing && event.pageY < ($(window).scrollTop() + 25) ){
                 
            if ($.cookie('modal_shown') == null) {
                $.cookie('modal_shown', 'yes', { expires: 3, path: '/' });
                $('#modal1').openModal();
                analytics.track('Modal Shown', {
                category: 'Newsletter',
                label: 'New Shops',
                value: 1
                });
            }
                     
               
            }
    });
  
     jQuery(document).ready(function() {
        $('.collapsible').collapsible({
            accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
        });
        $('.parallax').parallax();
        $(".button-collapse").sideNav();
        
        // var iOS = ( navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false );
        // console.log(iOS);
        // if (iOS) {
        //     $( "a" ).click(function(e) {
        //        window.alert = function() {};
        //       var link =  $(this).data('deeplink');
        //       var url = $(this).attr("href");
        //       setTimeout(function () { window.location = url; }, 1);
        //         window.location = link;
        //       // window.location.replace();
        //      console.log(link);
        //      e.stopPropagation();
        //         e.preventDefault();
        //     });
            
        // }
        
        if ($.cookie('email-cta') == null) {
        jQuery(".email-cta").delay(2000).slideDown(500);
        }


        if ($.cookie('search-box') == null) { 
        jQuery(".search-box").hide();
        jQuery(".search-box").slideDown(1000);
        $.cookie('search-box', 'yes', { expires: 365, path: '/' });
        } else {
            jQuery(".search-box").show();
        }

        jQuery('ul.tabs').tabs();

        // When user starts typing the form
        jQuery('#emailField').one("keypress", function () {
            analytics.track('Typing', {
            category: 'Newsletter',
            label: 'New Shops'
            
            });
        });
    });

    jQuery( ".email-form" ).submit(function( event ) {
        event.preventDefault();
        ajaxurl = "/wp-admin/admin-ajax.php";
        jQuery('.progress').slideDown();
      
        var sendIt = jQuery.post( ajaxurl, jQuery( this ).serialize());
        
        sendIt.done(function( data ) {
            console.log(data);
            jQuery('.email-form').slideUp();
            jQuery('.success').slideDown();
            $.cookie('modal_shown', 'yes', { expires: 365, path: '/' });
            $.cookie('email-cta', 'yes', { expires: 365, path: '/' });
            // When the form is submitted
            analytics.track('Subscribed', {
            category: 'Newsletter',
            label: 'New Shops',
            value: 1
            });
        });
    });

     jQuery( "#betaForm" ).submit(function( event ) {
        event.preventDefault();
        jQuery('.progress').slideDown();
        ajaxurl = "/wp-admin/admin-ajax.php";
        var checked = []
        $("input[name='platform[]']:checked").each(function ()
        {
            checked.push($(this).val());
        });
        checked = checked.join(", ");
        
        var sendIt = jQuery.post( ajaxurl, jQuery( this ).serialize() + "&platforms=" + checked);
        
        sendIt.done(function( data ) {

            $("html, body").animate({ scrollTop: 0 }, "slow");
            jQuery('#joinBeta').slideUp();
            jQuery('#joinedBeta').slideDown();

            $.cookie('modal_shown', 'yes', { expires: 365, path: '/' });
            $.cookie('email-cta', 'yes', { expires: 365, path: '/' });
            analytics.track('Subscribed', {
            category: 'Newsletter',
            label: 'Beta',
            value: 1
            });
    
        });
    });

    $( '#search-button' ).click(function(){
        $('.search').slideDown(500).focus();
    });

    $('.search').mouseleave(function(){
    	$('.search').slideUp();
    });

   $( '.datepicker' ).pickadate({
       selectMonths: true,
       selectYears: 15
   });

   $( '.offer-price-trigger' ).on( 'change', function() {

       var amount = $( this ).data( 'value' );

       var multiplier = $( this ).prop( 'checked' ) ? 1 : -1;

       var cost = $( this ).parents( 'form' ).find( '.cost' );

       var current = cost.text();

       var new_cost = current * 1 + ( multiplier * amount );

       cost.text( new_cost );

   } );

   $(document).ready(function() {

       $( 'select' ).material_select();

       $( '.modal-trigger' ).leanModal();

       var edit_modal = $( '#edit-modal' );

       if( edit_modal.length > 0 )
       {

           var edit_text = edit_modal.find( 'textarea' );

           edit_modal.find( '.edit-options' ).find( 'a' ).click( function( e ) {

               e.preventDefault();

               var text;

               if( typeof $( this ).attr( 'data-text' ) !== 'undefined' )
               {
                   text = $( this ).attr( 'data-text' );
               }
               else
               {
                   text = $( this ).text();
               }

               if( text.length > 0 )
               {
                   text += '. ';
               }

               edit_text.val( text );

               edit_text.trigger( 'change' );

               edit_text.focus();

               if (typeof edit_text[0].selectionStart == "number") {
                   edit_text[0].selectionStart = edit_text[0].selectionEnd = edit_text[0].value.length;
               } else if (typeof edit_text[0].createTextRange != "undefined") {
                   edit_text[0].focus();
                   var range = edit_text[0].createTextRange();
                   range.collapse(false);
                   range.select();
               }

           } );

       }

       var fct;

       $( window ).resize( fct = function() {
           $( '.card-empty' ).each( function() {

               var parent = $( this ).parent( '.col' );

               var prev = parent.prev( '.col' );

               var height = false;

               if( prev.length < 1 )
               {
                   prev = parent.next( '.col' );

                   if( prev.length < 1 )
                   {
                       height = $( parent ).width();
                   }
               }

               if( ! height )
               {
                   height = prev.height();
               }

               $( this ).css( 'height', height + 'px' );

           } );

       } );

       $( window ).load( function() {

           fct();

       } );

       $( '.checkbox-check-all' ).each( function() {
           
           var others = $( this ).parents( '.checkboxes-wrapper' ).find( ':checkbox' );

           if( others.length < 1 )
           {
               return true;
           }

           $( this ).change( function() {

               others.prop( 'checked', $( this ).prop( 'checked' ) );

           } );

       } );
       
       var instagram = $( '#instagram-photos' );
       
       var photos = instagram.find( '.instagram-photo' );
       
       var main_photo =  $( '.single-photo' );

       main_photo.data( 'default', main_photo.attr( 'src' ) );
       main_photo.data( 'default_set', main_photo.attr( 'srcset' ) );

       photos.click( function( e ) {

           e.preventDefault();

           var is_selected = $( this ).hasClass( 'active' );

           if( is_selected )
           {

               main_photo.attr( 'src', main_photo.data( 'default' ) );
               main_photo.attr( 'srcset', main_photo.data( 'default_set' ) );

               photos.removeClass( 'active' );
               $( this ).removeClass( 'active' );

           }
           else
           {

               main_photo.attr( 'src', $( this ).find( 'img' ).attr( 'src' ) );
               main_photo.attr( 'srcset', $( this ).find( 'img' ).attr( 'src' ) );

               photos.removeClass( 'active' );
               $( this ).addClass( 'active' );

           }

       } );

   });
   
   $( window ).on( 'adbeus_instagram_import', function( e, data ) {
       
       if( ! data )
       {
           data = e.data;
       }
       
       if( ! data.container )
       {
           return;
       }
       
       var container = $( data.container );
       
       if( container.length < 1 )
       {
           return;
       }
       
       var photos = container.find( '.photo-item' );

       var ids_field = container.find( '.ids-field' );
       
       photos.click( function( e ) {
           
           e.preventDefault();
           
           var is_selected = $( this ).hasClass( 'active' );
           
           var id = $( this ).data( 'id' );

           var added_id_field = container.find( '.ids-field.id-field-' + id );

           if( is_selected )
           {

               if( added_id_field.length > 0 )
               {
                   added_id_field.remove();
               }
               
               $( this ).removeClass( 'active' );
               
           }
           else 
           {
               
               if( added_id_field.length < 1 )
               {
                   added_id_field = ids_field.clone( true );
                   added_id_field.addClass( 'id-field-' + id );
                   added_id_field.val( id );
                   ids_field.after( added_id_field );
               }

               $( this ).addClass( 'active' );

           }
           
       } );
       
   } );