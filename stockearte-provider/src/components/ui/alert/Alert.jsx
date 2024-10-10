import React from 'react'

export const Alert = () => {
    const getAlertStyle = (type) => {
        switch (type) {
            case 'success':
                return { backgroundColor: 'green', color: 'white' };
            case 'error':
                return { backgroundColor: 'red', color: 'white' };
            case 'warning':
                return { backgroundColor: 'orange', color: 'black' };
            default:
                return { backgroundColor: 'gray', color: 'white' };
        }
    };

    return (
        <div style={{ padding: '10px', borderRadius: '5px', ...getAlertStyle(type) }}>
            {message}
        </div>
    );
};

