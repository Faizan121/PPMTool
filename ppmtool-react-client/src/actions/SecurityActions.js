import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER} from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUsers = (newUser, history ) =>  async dispatch =>{

    try {
        await axios.post("/api/users/register", newUser);
        history.push("/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
};

export const login = loginRequest => async dispatch =>{

    try {
        //post => Login Request
        const res = await axios.post("/api/users/login", loginRequest);
        //extract token res.data
        const {token} = res.data;
        //store the token in the localStorage
        localStorage.setItem("jwtToken", token);
        //set our token in header
        setJWTToken(token);
        //decode token on React
        const decoded  = jwt_decode(token);
        // dispatch to our securityReducer
        dispatch({
            type: SET_CURRENT_USER,
            payload: decoded
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }

};