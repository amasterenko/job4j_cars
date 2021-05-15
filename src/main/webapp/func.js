
function restoreFilterSettings() {
    $("#minPrice").val(localStorage.getItem("carFilterMinPrice"));
    $("#maxPrice").val(localStorage.getItem("carFilterMaxPrice"));
    $("#minKm").val( localStorage.getItem("carFilterMinKm"));
    $("#maxKm").val(localStorage.getItem("carFilterMaxKm"));
    $("#minYear").val(localStorage.getItem("carFilterMinYear"));
    $("#maxYear").val(localStorage.getItem("carFilterMaxYear"));
    $("#selectMake").val(localStorage.getItem("carFilterMake"));
    $("#selectModel").val(localStorage.getItem("carFilterModel"));
    $("#selectEngine").val( localStorage.getItem("carFilterEngine"));
    $("#selectDays").val( localStorage.getItem("carFilterDays"));
    return true;
}

function resetFilterSettings() {
    $("#minPrice").val('');
    $("#maxPrice").val('');
    $("#minKm").val('');
    $("#maxKm").val('');
    $("#minYear").val('');
    $("#maxYear").val('');
    $("#selectMake").val(0);
    $("#selectModel").val(0);
    $("#selectEngine").val(0);
    $("#selectDays").val('');
    localStorage.removeItem("carFilterMinPrice");
    localStorage.removeItem("carFilterMaxPrice");
    localStorage.removeItem("carFilterMinKm",);
    localStorage.removeItem("carFilterMaxKm");
    localStorage.removeItem("carFilterMinYear");
    localStorage.removeItem("carFilterMaxYear");
    localStorage.removeItem("carFilterMake");
    localStorage.removeItem("carFilterModel");
    localStorage.removeItem("carFilterEngine");
    localStorage.removeItem("carFilterDays");
    return true;
}

/**
 * Receives JSON from the server and fills the car model select control with
 * the categories.
 * @returns {boolean}
 */
function fillCarModelsSelectControl(selMake, selModels){
    let fullPath = window.location.href;
    let path = fullPath.substring(0, fullPath.lastIndexOf('/')) + '/models';
    selModels.prop("disabled", true);
    $.ajax({
        type: 'GET',
        url: path,
        data: 'makeid=' + selMake.value,
        dataType: 'json',
        success: function (data) {
            let existingValue = $("#sel1").val();
            let items;
            const models = data;
            for (let i = 0; i < models.length; i++) {
                items += "<option value='" + models[i].id + "'>" + models[i].name + "</option>";
            }
            selModels.prop("disabled", false);
            selModels.html(items);
            selModels.append('<option value="" selected disabled>-------</option>');
        }
    })
    return true;
}

/*ads functions*/

/**
 * Sets default filter parameters and saves filer's settings to
 * the localstorage.
 * @returns {boolean}
 */
function setDefaultFilterFieldsAndSaveSettings() {
    let minPice = $("#minPrice").val();
    if(!minPice) {
        $("#minPrice").val(0);
    }
    let maxPice = $("#maxPrice").val();
    if(!maxPice) {
        $("#maxPrice").val(100000000);
    }
    let minKm = $("#minKm").val();
    if(!minKm) {
        $("#minKm").val(0);
    }
    let maxKm = $("#maxKm").val();
    if(!maxKm) {
        $("#maxKm").val(100000000);
    }
    let minYear = $("#minYear").val();
    if(!minYear) {
        $("#minYear").val(1900);
    }
    let maxYear = $("#maxYear").val();
    if(!maxYear) {
        $("#maxYear").val(2100);
    }
    localStorage.setItem("carFilterMinPrice", minPice);
    localStorage.setItem("carFilterMaxPrice", maxPice);
    localStorage.setItem("carFilterMinKm", minKm);
    localStorage.setItem("carFilterMaxKm", maxKm);
    localStorage.setItem("carFilterMinYear", minYear);
    localStorage.setItem("carFilterMaxYear", maxYear);
    localStorage.setItem("carFilterMake", $("#selectMake").val());
    localStorage.setItem("carFilterModel", $("#selectModel").val());
    localStorage.setItem("carFilterEngine", $("#selectEngine").val());
    localStorage.setItem("carFilterDays", $("#selectDays").val());
    return true;
}

/*adedit functions*/
/**
 * Disable engine displacement input control if
 * the electric engine type was chosen.
 * @returns {boolean}
 */
function disableEngineDispInput() {
    if ($("#selectEngine option:selected").text().toLowerCase() === '"electric"') {
        $("#inputEngDisp").val("0.0");
        $("#inputEngDisp").prop("disabled", true);
        return true;
    }
    $("#inputEngDisp").prop("disabled", false);
    return true;
}
/**
 * Validates number of the photo files.
 * @returns {boolean}
 */
function validateFiles() {
    if ($("#inputFiles")[0].files.length > 4) {
        $("#msg").html('<div class="alert alert-warning d-flex text-center" role="alert">'
            + 'Choose less than 4 photos, please.' + '</div>');
        return false;
    }
    return true;
}
