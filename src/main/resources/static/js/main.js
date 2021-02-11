let translatedMessages = null;

function updateTranslatedMessages() {
    $.ajax({
        url: '/api/public/messages',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            translatedMessages = data;
        }
    });
}

$(document).ready(updateTranslatedMessages());

function getTranslatedMessage(key) {
    if(typeof translatedMessages[key] === 'undefined') {
        return 'Missing Key [key]';
    } else {
        return translatedMessages[key];
    }
}

let vueStartPage = null;
const accountEndpoint = '/api/public/account';
vueStartPage = new Vue({
    el: '#vueStartPage',
    data: {
        context: 'login',
        fullName: null,
        userName: null,
        email: null,
        password: null,
        message: null,
        isError: true,
        formValidationErrors: {
            fullName: null,
            userName: null,
            email: null,
            password: null
        }
    },
    computed: {
        isRegistration: function() {
            return this.context === 'registration';
        },
        getMessageClass: function() {
            return this.isError ? 'alert-danger' : 'alert-success';
        }
    },
    methods: {
        changeContext: function(newContext) {
            this.context = newContext;
        },
        onSubmit: function() {
            if(this.context === 'registration') {
                this.doRegistration();
            }  else {
                this.doLogin();
            }
        },
        doLogin: function () {
            let that = this;
            if(that.validateLoginRegistrationForm()) {
                $.ajax({
                    url: accountEndpoint + '/login',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        login: that.userName,
                        password: that.password
                    },
                    success: function (data) {
                        that.isError = !data.authenticated;
                        that.message = data.message;
                        if(data.authenticated) {
                            setTimeout(function() {
                                location.reload();
                            }, 500);
                        }
                    },
                    error: function() {
                        that.isError = true;
                        that.message = 'Beim Login ist ein Fehler aufgetreten';
                    }
                });
            }
        },
        doRegistration: function () {
            let that = this;
            if(that.validateLoginRegistrationForm()) {
                $.ajax({
                    url: accountEndpoint + '/register',
                    type: 'POST',
                    dataType: 'json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        login: that.userName,
                        name: that.fullName,
                        email: that.email,
                        password: that.password
                    }),
                    success: function (data) {
                        that.isError = !data.status;
                        that.message = data.message;
                    },
                    error: function() {
                        that.isError = true;
                        that.message = 'Bei der Registrierung ist ein Fehler aufgetreten';
                    }
                });
            }
        },
        validateLoginRegistrationForm: function () {
            let isValid = true;
            this.formValidationErrors = {
                fullName: null,
                userName: null,
                email: null,
                password: null
            };

            if(this.userName === null) {
                this.formValidationErrors.userName = getTranslatedMessage('formValidation.error.userName');
                isValid = false;
            }

            if(this.fullName === null && this.context === 'registration') {
                this.formValidationErrors.fullName = getTranslatedMessage('formValidation.error.fullName');
                isValid = false;
            }

            if(this.email === null && this.context === 'registration') {
                this.formValidationErrors.email = getTranslatedMessage('formValidation.error.email');
                isValid = false;
            }

            if(this.password === null) {
                this.formValidationErrors.password = getTranslatedMessage('formValidation.error.password');
                isValid = false;
            }

            return isValid;
        }
    }
});

