// util/toastUtil.js
import { toast } from "react-toastify";

export const showSuccess = (msg) => {
  toast.success(msg, {
    position: "bottom-right",
    autoClose: 2000,
    hideProgressBar: false,
  });
};

export const showError = (msg) => {
  toast.error(msg, {
    position: "bottom-right",
    autoClose: 2000,
    hideProgressBar: false,
  });
};
