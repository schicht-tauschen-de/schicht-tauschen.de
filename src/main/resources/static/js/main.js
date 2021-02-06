let vueStartPage = null;
const accountEndpoint = '/api/public/account';
$(document).ready(function() {
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
            formValidationErrors: []
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
            doLogin: function () {
                let that = this;
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
                        if(data.authenticated) {
                            let name = data.account.name;
                            that.message = 'Hallo ' + name + ',<br>der Login war erfolgreich';
                        } else {
                            that.message = 'Leider war der Login nicht erfolgreich';
                        }
                    },
                    error: function() {
                        that.isError = true;
                        that.message = 'Beim Login ist ein Fehler aufgetreten';
                    }
                });
            },
            doRegistration: function () {
                let that = this;
                if(that.validateRegistrationForm()) {
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
                        success: function (isSuccessful) {
                            that.isError = !isSuccessful;
                            if(isSuccessful) {
                                that.message = 'Registrierung erfolgreich. Bitte gucke in dein E-Mail Postfach nach.';
                            } else {
                                that.message = 'Registrierung nicht erfolgreich!';
                            }
                        },
                        error: function() {
                            that.isError = true;
                            that.message = 'Bei der Registrierung ist ein Fehler aufgetreten';
                        }
                    });
                }
            },
            validateRegistrationForm: function () {
                this.formValidationErrors = [];
                if(this.userName === null)
                    this.formValidationErrors.push('Du musst einen Benutzernamen wählen');

                if(this.fullName === null)
                    this.formValidationErrors.push('Du musst deinen Namen angeben');

                if(this.email === null)
                    this.formValidationErrors.push('Du musst deine E-Mail Adresse angeben');

                if(this.password === null)
                    this.formValidationErrors.push('Du musst ein Passwort wählen');

                return this.formValidationErrors.length === 0;
            }
        }
    });

});