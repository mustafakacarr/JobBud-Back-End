package com.jobbud.ws.controllers;
import com.jobbud.ws.entities.WalletEntity;
import com.jobbud.ws.responses.WalletHistoryResponse;
import com.jobbud.ws.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WalletControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    void testGetWalletByUserId() throws Exception {
        long userId = 1L;
        WalletEntity mockWallet = new WalletEntity(); // Create a mock WalletEntity for testing

        when(walletService.getWalletByUserId(anyLong())).thenReturn(mockWallet);

        mockMvc.perform(get("/api/v1.0/wallets").param("userId", String.valueOf(userId)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWalletById() throws Exception {
        long walletId = 1L;
        WalletEntity mockWallet = new WalletEntity(); // Create a mock WalletEntity for testing

        when(walletService.getWalletById(anyLong())).thenReturn(mockWallet);

        mockMvc.perform(get("/api/v1.0/wallets/{walletId}", walletId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetWalletHistoryById() throws Exception {
        long walletId = 1L;
        List<WalletHistoryResponse> mockHistory = Collections.emptyList(); // Create a mock history list for testing

        when(walletService.getWalletHistory(anyLong())).thenReturn(mockHistory);

        mockMvc.perform(get("/api/v1.0/wallets/{walletId}/history", walletId))
                .andExpect(status().isOk());
    }
}


