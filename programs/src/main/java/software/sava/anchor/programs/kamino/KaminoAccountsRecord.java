package software.sava.anchor.programs.kamino;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public record KaminoAccountsRecord(AccountMeta invokedProgram,
                                   PublicKey scopePrices,
                                   PublicKey farmProgram) implements KaminoAccounts {

  static final String LENDING_MARKET_AUTH = "lma";
  static final String RESERVE_LIQ_SUPPLY = "reserve_liq_supply";
  static final String FEE_RECEIVER = "fee_receiver";
  static final String RESERVE_COLL_MINT = "reserve_coll_mint";
  static final String RESERVE_COLL_SUPPLY = "reserve_coll_supply";
  static final String BASE_SEED_REFERRER_TOKEN_STATE = "referrer_acc";
  static final String BASE_SEED_USER_METADATA = "user_meta";
  static final String BASE_SEED_REFERRER_STATE = "ref_state";
  static final String BASE_SEED_SHORT_URL = "short_url";

}
