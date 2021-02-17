let Translation = function() {
    let translatedMessage = null;
    this.init = function() {
        let that = this;
        $.ajax({
            url: '/api/public/messages',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                that.translatedMessages  = data;
            }
        });
    };
    this.getMessage = function(key, parameters) {
        if(typeof this.translatedMessages[key] === 'undefined') {
            return 'Missing Key >[key]<';
        } else {
            let message = this.translatedMessages[key];
            if(parameters && parameters.length > 0) {
                for(let i = 0; i < parameters.length; i++) {
                    let regEx = new RegExp('\\{' + i + '\\}', 'g');
                    message = message.replace(regEx, parameters[i]);
                }
            }
            return message;
        }
    }
};

let translationService = new Translation();
translationService.init();

const publicAccountEndpoint = '/api/public/account';
const accountEndpoint = '/api/account';
const offerEndpoint = '/api/offer';

let vueStartPage = null;

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
                    url: publicAccountEndpoint + '/login',
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
                    url: publicAccountEndpoint + '/register',
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
                this.formValidationErrors.userName = translationService.getMessage('formValidation.error.userName');
                isValid = false;
            }

            if(this.fullName === null && this.context === 'registration') {
                this.formValidationErrors.fullName = translationService.getMessage('formValidation.error.fullName');
                isValid = false;
            }

            if(this.email === null && this.context === 'registration') {
                this.formValidationErrors.email = translationService.getMessage('formValidation.error.email');
                isValid = false;
            }

            if(this.password === null) {
                this.formValidationErrors.password = translationService.getMessage('formValidation.error.password');
                isValid = false;
            }

            return isValid;
        }
    }
});

let vueSearch = null;
vueSearch = new Vue({
    el: '#vueSearch',
    components: {
        vuejsDatepicker
    },
    data: {
        offerTypes: [],
        companies: [],
        departments: [],
        offers: [],
        selectedOfferType: 'OFFER',
        selectedCompany: null,
        startDate: null,
        endDate: null
    },
    mounted: function() {
        this.getFilterData();
        this.startDate = new Date();
        this.endDate = new Date();
        this.endDate.setDate(this.startDate.getDate() + 7);
    },
    methods: {
        formatDateToInputDate: function(date) {
          return date.toISOString().split("T")[0];
        },
        getFilterData: function() {
            let that = this;
            $.ajax({
                url: accountEndpoint + '/details',
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    if(data.companies.length > 0) {
                        for(let i = 0; i < data.companies.length; i++) {
                            that.companies.push(data.companies[i].company);
                        }
                    }
                }
            });
        },
        isCompanySelected: function(company) {
            if(this.selectedCompany === null) {
                return false;
            }
            return this.selectedCompany.id === company.id;
        },
        selectCompany: function(company) {
            let that = this;
            that.selectedCompany = company;
            let searchParameter = {
                type: that.selectedOfferType,
                companyId: company.id,
                departmentId: company.department.id,
                startDate: that.startDate,
                endDate: that.endDate,
                page: 0,
                pageSize: 10
            };
            $.ajax({
                url: offerEndpoint + '/search',
                type: 'GET',
                data: searchParameter,
                dataType: 'json',
                success: function (data) {
                    console.log('Data loaded')
                }
            });

        }
    }
});