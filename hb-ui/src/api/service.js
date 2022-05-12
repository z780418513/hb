import requests from "@/api/request";

export const getCaptcha = ()=> requests.get('/captcha')