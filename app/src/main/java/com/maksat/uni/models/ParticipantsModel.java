package com.maksat.uni.models;

import java.util.List;

public class ParticipantsModel {
    public static class item {
        public static class title {
            private Integer id;
            private String nameRus;
            private String nameEng;
            private String nameAra;

            public title(Integer id, String nameRus, String nameEng, String nameAra) {
                this.id = id;
                this.nameRus = nameRus;
                this.nameEng = nameEng;
                this.nameAra = nameAra;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getNameRus() {
                return nameRus;
            }

            public void setNameRus(String nameRus) {
                this.nameRus = nameRus;
            }

            public String getNameEng() {
                return nameEng;
            }

            public void setNameEng(String nameEng) {
                this.nameEng = nameEng;
            }

            public String getNameAra() {
                return nameAra;
            }

            public void setNameAra(String nameAra) {
                this.nameAra = nameAra;
            }
        }

        public static class badgeType {
            public static class categories {
                private Integer id;
                private String nameRus;
                private String nameEng;
                private String nameAra;

                public categories(Integer id, String nameRus, String nameEng, String nameAra) {
                    this.id = id;
                    this.nameRus = nameRus;
                    this.nameEng = nameEng;
                    this.nameAra = nameAra;
                }

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getNameRus() {
                    return nameRus;
                }

                public void setNameRus(String nameRus) {
                    this.nameRus = nameRus;
                }

                public String getNameEng() {
                    return nameEng;
                }

                public void setNameEng(String nameEng) {
                    this.nameEng = nameEng;
                }

                public String getNameAra() {
                    return nameAra;
                }

                public void setNameAra(String nameAra) {
                    this.nameAra = nameAra;
                }
            }

            private Integer id;
            private String type;
            private String color;
            private String photo;
            private List<categories> categories;

            public badgeType(Integer id, String type, String color, String photo, List<item.badgeType.categories> categories) {
                this.id = id;
                this.type = type;
                this.color = color;
                this.photo = photo;
                this.categories = categories;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public List<item.badgeType.categories> getCategories() {
                return categories;
            }

            public void setCategories(List<item.badgeType.categories> categories) {
                this.categories = categories;
            }
        }

        public static class company {
            public static class sphere {
                private Integer id;
                private String nameRus;
                private String nameEng;
                private String nameAra;
                private Boolean isMassMedia;

                public sphere(Integer id, String nameRus, String nameEng, String nameAra, Boolean isMassMedia) {
                    this.id = id;
                    this.nameRus = nameRus;
                    this.nameEng = nameEng;
                    this.nameAra = nameAra;
                    this.isMassMedia = isMassMedia;
                }

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getNameRus() {
                    return nameRus;
                }

                public void setNameRus(String nameRus) {
                    this.nameRus = nameRus;
                }

                public String getNameEng() {
                    return nameEng;
                }

                public void setNameEng(String nameEng) {
                    this.nameEng = nameEng;
                }

                public String getNameAra() {
                    return nameAra;
                }

                public void setNameAra(String nameAra) {
                    this.nameAra = nameAra;
                }

                public Boolean getMassMedia() {
                    return isMassMedia;
                }

                public void setMassMedia(Boolean massMedia) {
                    isMassMedia = massMedia;
                }
            }

            private Integer id;
            private String nameRus;
            private String nameEng;
            private String positionRus;
            private String positionEng;
            private Integer countryCompanyHeadId;
            private String postAddress;
            private sphere sphere;
            private Integer sphereId;

            public company(Integer id, String nameRus, String nameEng, String positionRus, String positionEng, Integer countryCompanyHeadId, String postAddress, item.company.sphere sphere, Integer sphereId) {
                this.id = id;
                this.nameRus = nameRus;
                this.nameEng = nameEng;
                this.positionRus = positionRus;
                this.positionEng = positionEng;
                this.countryCompanyHeadId = countryCompanyHeadId;
                this.postAddress = postAddress;
                this.sphere = sphere;
                this.sphereId = sphereId;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getNameRus() {
                return nameRus;
            }

            public void setNameRus(String nameRus) {
                this.nameRus = nameRus;
            }

            public String getNameEng() {
                return nameEng;
            }

            public void setNameEng(String nameEng) {
                this.nameEng = nameEng;
            }

            public String getPositionRus() {
                return positionRus;
            }

            public void setPositionRus(String positionRus) {
                this.positionRus = positionRus;
            }

            public String getPositionEng() {
                return positionEng;
            }

            public void setPositionEng(String positionEng) {
                this.positionEng = positionEng;
            }

            public Integer getCountryCompanyHeadId() {
                return countryCompanyHeadId;
            }

            public void setCountryCompanyHeadId(Integer countryCompanyHeadId) {
                this.countryCompanyHeadId = countryCompanyHeadId;
            }

            public String getPostAddress() {
                return postAddress;
            }

            public void setPostAddress(String postAddress) {
                this.postAddress = postAddress;
            }

            public item.company.sphere getSphere() {
                return sphere;
            }

            public void setSphere(item.company.sphere sphere) {
                this.sphere = sphere;
            }

            public Integer getSphereId() {
                return sphereId;
            }

            public void setSphereId(Integer sphereId) {
                this.sphereId = sphereId;
            }
        }

        public static class category {
            private Integer id;
            private Integer eventCategoryId;
            private Integer eventId;
            private String nameRus;
            private String nameEng;
            private String nameAra;
            private String descriptionRus;
            private String descriptionEng;
            private String descriptionAra;
            private String code;
            private String color;
            private Integer priceRouble;
            private Integer priceDollar;

            public category(Integer id, Integer eventCategoryId, Integer eventId, String nameRus, String nameEng, String nameAra, String descriptionRus, String descriptionEng, String descriptionAra, String code, String color, Integer priceRouble, Integer priceDollar) {
                this.id = id;
                this.eventCategoryId = eventCategoryId;
                this.eventId = eventId;
                this.nameRus = nameRus;
                this.nameEng = nameEng;
                this.nameAra = nameAra;
                this.descriptionRus = descriptionRus;
                this.descriptionEng = descriptionEng;
                this.descriptionAra = descriptionAra;
                this.code = code;
                this.color = color;
                this.priceRouble = priceRouble;
                this.priceDollar = priceDollar;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getEventCategoryId() {
                return eventCategoryId;
            }

            public void setEventCategoryId(Integer eventCategoryId) {
                this.eventCategoryId = eventCategoryId;
            }

            public Integer getEventId() {
                return eventId;
            }

            public void setEventId(Integer eventId) {
                this.eventId = eventId;
            }

            public String getNameRus() {
                return nameRus;
            }

            public void setNameRus(String nameRus) {
                this.nameRus = nameRus;
            }

            public String getNameEng() {
                return nameEng;
            }

            public void setNameEng(String nameEng) {
                this.nameEng = nameEng;
            }

            public String getNameAra() {
                return nameAra;
            }

            public void setNameAra(String nameAra) {
                this.nameAra = nameAra;
            }

            public String getDescriptionRus() {
                return descriptionRus;
            }

            public void setDescriptionRus(String descriptionRus) {
                this.descriptionRus = descriptionRus;
            }

            public String getDescriptionEng() {
                return descriptionEng;
            }

            public void setDescriptionEng(String descriptionEng) {
                this.descriptionEng = descriptionEng;
            }

            public String getDescriptionAra() {
                return descriptionAra;
            }

            public void setDescriptionAra(String descriptionAra) {
                this.descriptionAra = descriptionAra;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public Integer getPriceRouble() {
                return priceRouble;
            }

            public void setPriceRouble(Integer priceRouble) {
                this.priceRouble = priceRouble;
            }

            public Integer getPriceDollar() {
                return priceDollar;
            }

            public void setPriceDollar(Integer priceDollar) {
                this.priceDollar = priceDollar;
            }
        }

        public static class corporateAccessMainParticipant {
            public static class company {
                private Integer id;
                private String nameRus;
                private String nameEng;
                private String positionRus;
                private String positionEng;
                private Integer countryCompanyHeadId;
                private String postAddress;
                private sphere sphere;

                public static class sphere {
                    private Integer id;
                    private String nameRus;
                    private String nameEng;
                    private String nameAra;
                    private Boolean isMassMedia;

                    public sphere(Integer id, String nameRus, String nameEng, String nameAra, Boolean isMassMedia) {
                        this.id = id;
                        this.nameRus = nameRus;
                        this.nameEng = nameEng;
                        this.nameAra = nameAra;
                        this.isMassMedia = isMassMedia;
                    }

                    public Integer getId() {
                        return id;
                    }

                    public void setId(Integer id) {
                        this.id = id;
                    }

                    public String getNameRus() {
                        return nameRus;
                    }

                    public void setNameRus(String nameRus) {
                        this.nameRus = nameRus;
                    }

                    public String getNameEng() {
                        return nameEng;
                    }

                    public void setNameEng(String nameEng) {
                        this.nameEng = nameEng;
                    }

                    public String getNameAra() {
                        return nameAra;
                    }

                    public void setNameAra(String nameAra) {
                        this.nameAra = nameAra;
                    }

                    public Boolean getMassMedia() {
                        return isMassMedia;
                    }

                    public void setMassMedia(Boolean massMedia) {
                        isMassMedia = massMedia;
                    }
                }

                private Integer sphereId;

                public company(Integer id, String nameRus, String nameEng, String positionRus, String positionEng, Integer countryCompanyHeadId, String postAddress, item.corporateAccessMainParticipant.company.sphere sphere, Integer sphereId) {
                    this.id = id;
                    this.nameRus = nameRus;
                    this.nameEng = nameEng;
                    this.positionRus = positionRus;
                    this.positionEng = positionEng;
                    this.countryCompanyHeadId = countryCompanyHeadId;
                    this.postAddress = postAddress;
                    this.sphere = sphere;
                    this.sphereId = sphereId;
                }

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getNameRus() {
                    return nameRus;
                }

                public void setNameRus(String nameRus) {
                    this.nameRus = nameRus;
                }

                public String getNameEng() {
                    return nameEng;
                }

                public void setNameEng(String nameEng) {
                    this.nameEng = nameEng;
                }

                public String getPositionRus() {
                    return positionRus;
                }

                public void setPositionRus(String positionRus) {
                    this.positionRus = positionRus;
                }

                public String getPositionEng() {
                    return positionEng;
                }

                public void setPositionEng(String positionEng) {
                    this.positionEng = positionEng;
                }

                public Integer getCountryCompanyHeadId() {
                    return countryCompanyHeadId;
                }

                public void setCountryCompanyHeadId(Integer countryCompanyHeadId) {
                    this.countryCompanyHeadId = countryCompanyHeadId;
                }

                public String getPostAddress() {
                    return postAddress;
                }

                public void setPostAddress(String postAddress) {
                    this.postAddress = postAddress;
                }

                public item.corporateAccessMainParticipant.company.sphere getSphere() {
                    return sphere;
                }

                public void setSphere(item.corporateAccessMainParticipant.company.sphere sphere) {
                    this.sphere = sphere;
                }

                public Integer getSphereId() {
                    return sphereId;
                }

                public void setSphereId(Integer sphereId) {
                    this.sphereId = sphereId;
                }
            }

            private Integer id;
            private String firstNameRus;
            private String lastNameRus;
            private String patronymic;
            private String firstNameEng;
            private String lastNameEng;
            private company company;
            private String positionRus;
            private String positionEng;
            private Integer participantCorporateAccessId;

            public corporateAccessMainParticipant(Integer id, String firstNameRus, String lastNameRus, String patronymic, String firstNameEng, String lastNameEng, item.corporateAccessMainParticipant.company company, String positionRus, String positionEng, Integer participantCorporateAccessId) {
                this.id = id;
                this.firstNameRus = firstNameRus;
                this.lastNameRus = lastNameRus;
                this.patronymic = patronymic;
                this.firstNameEng = firstNameEng;
                this.lastNameEng = lastNameEng;
                this.company = company;
                this.positionRus = positionRus;
                this.positionEng = positionEng;
                this.participantCorporateAccessId = participantCorporateAccessId;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getFirstNameRus() {
                return firstNameRus;
            }

            public void setFirstNameRus(String firstNameRus) {
                this.firstNameRus = firstNameRus;
            }

            public String getLastNameRus() {
                return lastNameRus;
            }

            public void setLastNameRus(String lastNameRus) {
                this.lastNameRus = lastNameRus;
            }

            public String getPatronymic() {
                return patronymic;
            }

            public void setPatronymic(String patronymic) {
                this.patronymic = patronymic;
            }

            public String getFirstNameEng() {
                return firstNameEng;
            }

            public void setFirstNameEng(String firstNameEng) {
                this.firstNameEng = firstNameEng;
            }

            public String getLastNameEng() {
                return lastNameEng;
            }

            public void setLastNameEng(String lastNameEng) {
                this.lastNameEng = lastNameEng;
            }

            public item.corporateAccessMainParticipant.company getCompany() {
                return company;
            }

            public void setCompany(item.corporateAccessMainParticipant.company company) {
                this.company = company;
            }

            public String getPositionRus() {
                return positionRus;
            }

            public void setPositionRus(String positionRus) {
                this.positionRus = positionRus;
            }

            public String getPositionEng() {
                return positionEng;
            }

            public void setPositionEng(String positionEng) {
                this.positionEng = positionEng;
            }

            public Integer getParticipantCorporateAccessId() {
                return participantCorporateAccessId;
            }

            public void setParticipantCorporateAccessId(Integer participantCorporateAccessId) {
                this.participantCorporateAccessId = participantCorporateAccessId;
            }
        }

        public static class orders {
            private String description;
            private String amountCurrency;
            private Integer amountValue;
            private String paymentDateTime;
            private Integer orderId;
            private Integer orderStatusId;
            private Integer corporatePaymentTypeId;
            private String addedDate;

            public orders(String description, String amountCurrency, Integer amountValue, String paymentDateTime, Integer orderId, Integer orderStatusId, Integer corporatePaymentTypeId, String addedDate) {
                this.description = description;
                this.amountCurrency = amountCurrency;
                this.amountValue = amountValue;
                this.paymentDateTime = paymentDateTime;
                this.orderId = orderId;
                this.orderStatusId = orderStatusId;
                this.corporatePaymentTypeId = corporatePaymentTypeId;
                this.addedDate = addedDate;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAmountCurrency() {
                return amountCurrency;
            }

            public void setAmountCurrency(String amountCurrency) {
                this.amountCurrency = amountCurrency;
            }

            public Integer getAmountValue() {
                return amountValue;
            }

            public void setAmountValue(Integer amountValue) {
                this.amountValue = amountValue;
            }

            public String getPaymentDateTime() {
                return paymentDateTime;
            }

            public void setPaymentDateTime(String paymentDateTime) {
                this.paymentDateTime = paymentDateTime;
            }

            public Integer getOrderId() {
                return orderId;
            }

            public void setOrderId(Integer orderId) {
                this.orderId = orderId;
            }

            public Integer getOrderStatusId() {
                return orderStatusId;
            }

            public void setOrderStatusId(Integer orderStatusId) {
                this.orderStatusId = orderStatusId;
            }

            public Integer getCorporatePaymentTypeId() {
                return corporatePaymentTypeId;
            }

            public void setCorporatePaymentTypeId(Integer corporatePaymentTypeId) {
                this.corporatePaymentTypeId = corporatePaymentTypeId;
            }

            public String getAddedDate() {
                return addedDate;
            }

            public void setAddedDate(String addedDate) {
                this.addedDate = addedDate;
            }
        }

        private Boolean accountExists;
        private Integer id;
        private Integer titleId;
        private Integer eventId;
        private Integer genderId;
        private Integer citizenshipId;
        private Integer residenceId;
        private Integer securityStatusId;
        private Integer acrStatusStepOneId;
        private Integer acrStatusStepTwoId;
        private Integer badgeStatusId;
        private Integer languageId;
        private title title;
        private String email;
        private String firstNameRus;
        private String lastNameRus;
        private String patronymic;
        private String firstNameEng;
        private String lastNameEng;
        private String birthday;
        private String phoneNumber;
        private String passportNumber;
        private String issuedBy;
        private String name;
        private String issuedDate;
        private String validUntilDate;
        private String createdDate;
        private String modifiedDate;
        private String birthPlace;
        private String photo;
        private String webSite;
        private String comment;
        private Boolean visa;
        private Integer sportId;
        private String sportNameRus;
        private String sportNameEng;
        private String sportCategoryName;
        private Integer regionId;
        private String regionNameRus;
        private String regionNameEng;
        private Boolean needHotel;
        private String checkInDateTime;
        private String checkOutDateTime;
        private Integer hotelId;
        private String hotelName;
        private String roomName;
        private Integer roomId;
        private String arrivalStation;
        private String arrivalDateTime;
        private String arrivalFlightInfo;
        private String departureStation;
        private String departureDateTime;
        private String departureFlightInfo;
        private String contractOfPayment;
        private badgeType badgeType;
        private company company;
        private category category;
        private Boolean isPaid;
        private Boolean isPaidByCorporate;
        private Boolean isFakePayment;
        private Boolean isCorporateAccess;
        private Boolean isCorporateAccessApproved;
        private corporateAccessMainParticipant corporateAccessMainParticipant;
        private List<orders> orders;

        public item(Boolean accountExists, Integer id, Integer titleId, Integer eventId, Integer genderId, Integer citizenshipId, Integer residenceId, Integer securityStatusId, Integer acrStatusStepOneId, Integer acrStatusStepTwoId, Integer badgeStatusId, Integer languageId, item.title title, String email, String firstNameRus, String lastNameRus, String patronymic, String firstNameEng, String lastNameEng, String birthday, String phoneNumber, String passportNumber, String issuedBy, String name, String issuedDate, String validUntilDate, String createdDate, String modifiedDate, String birthPlace, String photo, String webSite, String comment, Boolean visa, Integer sportId, String sportNameRus, String sportNameEng, String sportCategoryName, Integer regionId, String regionNameRus, String regionNameEng, Boolean needHotel, String checkInDateTime, String checkOutDateTime, Integer hotelId, String hotelName, String roomName, Integer roomId, String arrivalStation, String arrivalDateTime, String arrivalFlightInfo, String departureStation, String departureDateTime, String departureFlightInfo, String contractOfPayment, item.badgeType badgeType, item.company company, item.category category, Boolean isPaid, Boolean isPaidByCorporate, Boolean isFakePayment, Boolean isCorporateAccess, Boolean isCorporateAccessApproved, item.corporateAccessMainParticipant corporateAccessMainParticipant, List<item.orders> orders) {
            this.accountExists = accountExists;
            this.id = id;
            this.titleId = titleId;
            this.eventId = eventId;
            this.genderId = genderId;
            this.citizenshipId = citizenshipId;
            this.residenceId = residenceId;
            this.securityStatusId = securityStatusId;
            this.acrStatusStepOneId = acrStatusStepOneId;
            this.acrStatusStepTwoId = acrStatusStepTwoId;
            this.badgeStatusId = badgeStatusId;
            this.languageId = languageId;
            this.title = title;
            this.email = email;
            this.firstNameRus = firstNameRus;
            this.lastNameRus = lastNameRus;
            this.patronymic = patronymic;
            this.firstNameEng = firstNameEng;
            this.lastNameEng = lastNameEng;
            this.birthday = birthday;
            this.phoneNumber = phoneNumber;
            this.passportNumber = passportNumber;
            this.issuedBy = issuedBy;
            this.name = name;
            this.issuedDate = issuedDate;
            this.validUntilDate = validUntilDate;
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
            this.birthPlace = birthPlace;
            this.photo = photo;
            this.webSite = webSite;
            this.comment = comment;
            this.visa = visa;
            this.sportId = sportId;
            this.sportNameRus = sportNameRus;
            this.sportNameEng = sportNameEng;
            this.sportCategoryName = sportCategoryName;
            this.regionId = regionId;
            this.regionNameRus = regionNameRus;
            this.regionNameEng = regionNameEng;
            this.needHotel = needHotel;
            this.checkInDateTime = checkInDateTime;
            this.checkOutDateTime = checkOutDateTime;
            this.hotelId = hotelId;
            this.hotelName = hotelName;
            this.roomName = roomName;
            this.roomId = roomId;
            this.arrivalStation = arrivalStation;
            this.arrivalDateTime = arrivalDateTime;
            this.arrivalFlightInfo = arrivalFlightInfo;
            this.departureStation = departureStation;
            this.departureDateTime = departureDateTime;
            this.departureFlightInfo = departureFlightInfo;
            this.contractOfPayment = contractOfPayment;
            this.badgeType = badgeType;
            this.company = company;
            this.category = category;
            this.isPaid = isPaid;
            this.isPaidByCorporate = isPaidByCorporate;
            this.isFakePayment = isFakePayment;
            this.isCorporateAccess = isCorporateAccess;
            this.isCorporateAccessApproved = isCorporateAccessApproved;
            this.corporateAccessMainParticipant = corporateAccessMainParticipant;
            this.orders = orders;
        }

        public Boolean getAccountExists() {
            return accountExists;
        }

        public void setAccountExists(Boolean accountExists) {
            this.accountExists = accountExists;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getTitleId() {
            return titleId;
        }

        public void setTitleId(Integer titleId) {
            this.titleId = titleId;
        }

        public Integer getEventId() {
            return eventId;
        }

        public void setEventId(Integer eventId) {
            this.eventId = eventId;
        }

        public Integer getGenderId() {
            return genderId;
        }

        public void setGenderId(Integer genderId) {
            this.genderId = genderId;
        }

        public Integer getCitizenshipId() {
            return citizenshipId;
        }

        public void setCitizenshipId(Integer citizenshipId) {
            this.citizenshipId = citizenshipId;
        }

        public Integer getResidenceId() {
            return residenceId;
        }

        public void setResidenceId(Integer residenceId) {
            this.residenceId = residenceId;
        }

        public Integer getSecurityStatusId() {
            return securityStatusId;
        }

        public void setSecurityStatusId(Integer securityStatusId) {
            this.securityStatusId = securityStatusId;
        }

        public Integer getAcrStatusStepOneId() {
            return acrStatusStepOneId;
        }

        public void setAcrStatusStepOneId(Integer acrStatusStepOneId) {
            this.acrStatusStepOneId = acrStatusStepOneId;
        }

        public Integer getAcrStatusStepTwoId() {
            return acrStatusStepTwoId;
        }

        public void setAcrStatusStepTwoId(Integer acrStatusStepTwoId) {
            this.acrStatusStepTwoId = acrStatusStepTwoId;
        }

        public Integer getBadgeStatusId() {
            return badgeStatusId;
        }

        public void setBadgeStatusId(Integer badgeStatusId) {
            this.badgeStatusId = badgeStatusId;
        }

        public Integer getLanguageId() {
            return languageId;
        }

        public void setLanguageId(Integer languageId) {
            this.languageId = languageId;
        }

        public item.title getTitle() {
            return title;
        }

        public void setTitle(item.title title) {
            this.title = title;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstNameRus() {
            return firstNameRus;
        }

        public void setFirstNameRus(String firstNameRus) {
            this.firstNameRus = firstNameRus;
        }

        public String getLastNameRus() {
            return lastNameRus;
        }

        public void setLastNameRus(String lastNameRus) {
            this.lastNameRus = lastNameRus;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getFirstNameEng() {
            return firstNameEng;
        }

        public void setFirstNameEng(String firstNameEng) {
            this.firstNameEng = firstNameEng;
        }

        public String getLastNameEng() {
            return lastNameEng;
        }

        public void setLastNameEng(String lastNameEng) {
            this.lastNameEng = lastNameEng;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPassportNumber() {
            return passportNumber;
        }

        public void setPassportNumber(String passportNumber) {
            this.passportNumber = passportNumber;
        }

        public String getIssuedBy() {
            return issuedBy;
        }

        public void setIssuedBy(String issuedBy) {
            this.issuedBy = issuedBy;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIssuedDate() {
            return issuedDate;
        }

        public void setIssuedDate(String issuedDate) {
            this.issuedDate = issuedDate;
        }

        public String getValidUntilDate() {
            return validUntilDate;
        }

        public void setValidUntilDate(String validUntilDate) {
            this.validUntilDate = validUntilDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public void setBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getWebSite() {
            return webSite;
        }

        public void setWebSite(String webSite) {
            this.webSite = webSite;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Boolean getVisa() {
            return visa;
        }

        public void setVisa(Boolean visa) {
            this.visa = visa;
        }

        public Integer getSportId() {
            return sportId;
        }

        public void setSportId(Integer sportId) {
            this.sportId = sportId;
        }

        public String getSportNameRus() {
            return sportNameRus;
        }

        public void setSportNameRus(String sportNameRus) {
            this.sportNameRus = sportNameRus;
        }

        public String getSportNameEng() {
            return sportNameEng;
        }

        public void setSportNameEng(String sportNameEng) {
            this.sportNameEng = sportNameEng;
        }

        public String getSportCategoryName() {
            return sportCategoryName;
        }

        public void setSportCategoryName(String sportCategoryName) {
            this.sportCategoryName = sportCategoryName;
        }

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }

        public String getRegionNameRus() {
            return regionNameRus;
        }

        public void setRegionNameRus(String regionNameRus) {
            this.regionNameRus = regionNameRus;
        }

        public String getRegionNameEng() {
            return regionNameEng;
        }

        public void setRegionNameEng(String regionNameEng) {
            this.regionNameEng = regionNameEng;
        }

        public Boolean getNeedHotel() {
            return needHotel;
        }

        public void setNeedHotel(Boolean needHotel) {
            this.needHotel = needHotel;
        }

        public String getCheckInDateTime() {
            return checkInDateTime;
        }

        public void setCheckInDateTime(String checkInDateTime) {
            this.checkInDateTime = checkInDateTime;
        }

        public String getCheckOutDateTime() {
            return checkOutDateTime;
        }

        public void setCheckOutDateTime(String checkOutDateTime) {
            this.checkOutDateTime = checkOutDateTime;
        }

        public Integer getHotelId() {
            return hotelId;
        }

        public void setHotelId(Integer hotelId) {
            this.hotelId = hotelId;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        public String getArrivalStation() {
            return arrivalStation;
        }

        public void setArrivalStation(String arrivalStation) {
            this.arrivalStation = arrivalStation;
        }

        public String getArrivalDateTime() {
            return arrivalDateTime;
        }

        public void setArrivalDateTime(String arrivalDateTime) {
            this.arrivalDateTime = arrivalDateTime;
        }

        public String getArrivalFlightInfo() {
            return arrivalFlightInfo;
        }

        public void setArrivalFlightInfo(String arrivalFlightInfo) {
            this.arrivalFlightInfo = arrivalFlightInfo;
        }

        public String getDepartureStation() {
            return departureStation;
        }

        public void setDepartureStation(String departureStation) {
            this.departureStation = departureStation;
        }

        public String getDepartureDateTime() {
            return departureDateTime;
        }

        public void setDepartureDateTime(String departureDateTime) {
            this.departureDateTime = departureDateTime;
        }

        public String getDepartureFlightInfo() {
            return departureFlightInfo;
        }

        public void setDepartureFlightInfo(String departureFlightInfo) {
            this.departureFlightInfo = departureFlightInfo;
        }

        public String getContractOfPayment() {
            return contractOfPayment;
        }

        public void setContractOfPayment(String contractOfPayment) {
            this.contractOfPayment = contractOfPayment;
        }

        public item.badgeType getBadgeType() {
            return badgeType;
        }

        public void setBadgeType(item.badgeType badgeType) {
            this.badgeType = badgeType;
        }

        public item.company getCompany() {
            return company;
        }

        public void setCompany(item.company company) {
            this.company = company;
        }

        public item.category getCategory() {
            return category;
        }

        public void setCategory(item.category category) {
            this.category = category;
        }

        public Boolean getPaid() {
            return isPaid;
        }

        public void setPaid(Boolean paid) {
            isPaid = paid;
        }

        public Boolean getPaidByCorporate() {
            return isPaidByCorporate;
        }

        public void setPaidByCorporate(Boolean paidByCorporate) {
            isPaidByCorporate = paidByCorporate;
        }

        public Boolean getFakePayment() {
            return isFakePayment;
        }

        public void setFakePayment(Boolean fakePayment) {
            isFakePayment = fakePayment;
        }

        public Boolean getCorporateAccess() {
            return isCorporateAccess;
        }

        public void setCorporateAccess(Boolean corporateAccess) {
            isCorporateAccess = corporateAccess;
        }

        public Boolean getCorporateAccessApproved() {
            return isCorporateAccessApproved;
        }

        public void setCorporateAccessApproved(Boolean corporateAccessApproved) {
            isCorporateAccessApproved = corporateAccessApproved;
        }

        public item.corporateAccessMainParticipant getCorporateAccessMainParticipant() {
            return corporateAccessMainParticipant;
        }

        public void setCorporateAccessMainParticipant(item.corporateAccessMainParticipant corporateAccessMainParticipant) {
            this.corporateAccessMainParticipant = corporateAccessMainParticipant;
        }

        public List<item.orders> getOrders() {
            return orders;
        }

        public void setOrders(List<item.orders> orders) {
            this.orders = orders;
        }
    }

    private List<item> items;
    private Integer allItemsCount;
    private Integer pageNumber;
    private Integer itemOnPage;

    public ParticipantsModel(List<item> items, Integer allItemsCount, Integer pageNumber, Integer itemOnPage) {
        this.items = items;
        this.allItemsCount = allItemsCount;
        this.pageNumber = pageNumber;
        this.itemOnPage = itemOnPage;
    }

    public List<item> getItems() {
        return items;
    }

    public void setItems(List<item> items) {
        this.items = items;
    }

    public Integer getAllItemsCount() {
        return allItemsCount;
    }

    public void setAllItemsCount(Integer allItemsCount) {
        this.allItemsCount = allItemsCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getItemOnPage() {
        return itemOnPage;
    }

    public void setItemOnPage(Integer itemOnPage) {
        this.itemOnPage = itemOnPage;
    }
}

