package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Vault(PublicKey _address,
                    Discriminator discriminator,
                    // pool
                    PublicKey pool,
                    // reserve quote token
                    PublicKey tokenVault,
                    // reserve base token
                    PublicKey tokenOutVault,
                    // quote token
                    PublicKey quoteMint,
                    // base token
                    PublicKey baseMint,
                    // base key
                    PublicKey base,
                    // owner key, deprecated field, can re-use in the future
                    PublicKey owner,
                    // max buying cap
                    long maxBuyingCap,
                    // total deposited quote token
                    long totalDeposit,
                    // total user deposit
                    long totalEscrow,
                    // swapped_amount
                    long swappedAmount,
                    // total bought token
                    long boughtToken,
                    // Total quote refund
                    long totalRefund,
                    // Total claimed_token
                    long totalClaimedToken,
                    // Start vesting ts
                    long startVestingPoint,
                    // End vesting ts
                    long endVestingPoint,
                    // bump
                    int bump,
                    // pool type
                    int poolType,
                    // vault mode
                    int vaultMode,
                    // padding 0
                    byte[] padding0,
                    // max depositing cap
                    long maxDepositingCap,
                    // individual depositing cap
                    long individualDepositingCap,
                    // depositing point
                    long depositingPoint,
                    // flat fee when user open an escrow
                    long escrowFee,
                    // total escrow fee just for statistic
                    long totalEscrowFee,
                    // deposit whitelist mode
                    int whitelistMode,
                    // activation type
                    int activationType,
                    // padding 1
                    byte[] padding1,
                    // vault authority normally is vault creator, will be able to create merkle root config
                    PublicKey vaultAuthority,
                    BigInteger[] padding) implements Borsh {

  public static final int BYTES = 472;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int POOL_OFFSET = 8;
  public static final int TOKEN_VAULT_OFFSET = 40;
  public static final int TOKEN_OUT_VAULT_OFFSET = 72;
  public static final int QUOTE_MINT_OFFSET = 104;
  public static final int BASE_MINT_OFFSET = 136;
  public static final int BASE_OFFSET = 168;
  public static final int OWNER_OFFSET = 200;
  public static final int MAX_BUYING_CAP_OFFSET = 232;
  public static final int TOTAL_DEPOSIT_OFFSET = 240;
  public static final int TOTAL_ESCROW_OFFSET = 248;
  public static final int SWAPPED_AMOUNT_OFFSET = 256;
  public static final int BOUGHT_TOKEN_OFFSET = 264;
  public static final int TOTAL_REFUND_OFFSET = 272;
  public static final int TOTAL_CLAIMED_TOKEN_OFFSET = 280;
  public static final int START_VESTING_POINT_OFFSET = 288;
  public static final int END_VESTING_POINT_OFFSET = 296;
  public static final int BUMP_OFFSET = 304;
  public static final int POOL_TYPE_OFFSET = 305;
  public static final int VAULT_MODE_OFFSET = 306;
  public static final int PADDING0_OFFSET = 307;
  public static final int MAX_DEPOSITING_CAP_OFFSET = 312;
  public static final int INDIVIDUAL_DEPOSITING_CAP_OFFSET = 320;
  public static final int DEPOSITING_POINT_OFFSET = 328;
  public static final int ESCROW_FEE_OFFSET = 336;
  public static final int TOTAL_ESCROW_FEE_OFFSET = 344;
  public static final int WHITELIST_MODE_OFFSET = 352;
  public static final int ACTIVATION_TYPE_OFFSET = 353;
  public static final int PADDING1_OFFSET = 354;
  public static final int VAULT_AUTHORITY_OFFSET = 360;
  public static final int PADDING_OFFSET = 392;

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Filter createTokenVaultFilter(final PublicKey tokenVault) {
    return Filter.createMemCompFilter(TOKEN_VAULT_OFFSET, tokenVault);
  }

  public static Filter createTokenOutVaultFilter(final PublicKey tokenOutVault) {
    return Filter.createMemCompFilter(TOKEN_OUT_VAULT_OFFSET, tokenOutVault);
  }

  public static Filter createQuoteMintFilter(final PublicKey quoteMint) {
    return Filter.createMemCompFilter(QUOTE_MINT_OFFSET, quoteMint);
  }

  public static Filter createBaseMintFilter(final PublicKey baseMint) {
    return Filter.createMemCompFilter(BASE_MINT_OFFSET, baseMint);
  }

  public static Filter createBaseFilter(final PublicKey base) {
    return Filter.createMemCompFilter(BASE_OFFSET, base);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createMaxBuyingCapFilter(final long maxBuyingCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxBuyingCap);
    return Filter.createMemCompFilter(MAX_BUYING_CAP_OFFSET, _data);
  }

  public static Filter createTotalDepositFilter(final long totalDeposit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalDeposit);
    return Filter.createMemCompFilter(TOTAL_DEPOSIT_OFFSET, _data);
  }

  public static Filter createTotalEscrowFilter(final long totalEscrow) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalEscrow);
    return Filter.createMemCompFilter(TOTAL_ESCROW_OFFSET, _data);
  }

  public static Filter createSwappedAmountFilter(final long swappedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, swappedAmount);
    return Filter.createMemCompFilter(SWAPPED_AMOUNT_OFFSET, _data);
  }

  public static Filter createBoughtTokenFilter(final long boughtToken) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, boughtToken);
    return Filter.createMemCompFilter(BOUGHT_TOKEN_OFFSET, _data);
  }

  public static Filter createTotalRefundFilter(final long totalRefund) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalRefund);
    return Filter.createMemCompFilter(TOTAL_REFUND_OFFSET, _data);
  }

  public static Filter createTotalClaimedTokenFilter(final long totalClaimedToken) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalClaimedToken);
    return Filter.createMemCompFilter(TOTAL_CLAIMED_TOKEN_OFFSET, _data);
  }

  public static Filter createStartVestingPointFilter(final long startVestingPoint) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, startVestingPoint);
    return Filter.createMemCompFilter(START_VESTING_POINT_OFFSET, _data);
  }

  public static Filter createEndVestingPointFilter(final long endVestingPoint) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, endVestingPoint);
    return Filter.createMemCompFilter(END_VESTING_POINT_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createPoolTypeFilter(final int poolType) {
    return Filter.createMemCompFilter(POOL_TYPE_OFFSET, new byte[]{(byte) poolType});
  }

  public static Filter createVaultModeFilter(final int vaultMode) {
    return Filter.createMemCompFilter(VAULT_MODE_OFFSET, new byte[]{(byte) vaultMode});
  }

  public static Filter createMaxDepositingCapFilter(final long maxDepositingCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxDepositingCap);
    return Filter.createMemCompFilter(MAX_DEPOSITING_CAP_OFFSET, _data);
  }

  public static Filter createIndividualDepositingCapFilter(final long individualDepositingCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, individualDepositingCap);
    return Filter.createMemCompFilter(INDIVIDUAL_DEPOSITING_CAP_OFFSET, _data);
  }

  public static Filter createDepositingPointFilter(final long depositingPoint) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositingPoint);
    return Filter.createMemCompFilter(DEPOSITING_POINT_OFFSET, _data);
  }

  public static Filter createEscrowFeeFilter(final long escrowFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, escrowFee);
    return Filter.createMemCompFilter(ESCROW_FEE_OFFSET, _data);
  }

  public static Filter createTotalEscrowFeeFilter(final long totalEscrowFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalEscrowFee);
    return Filter.createMemCompFilter(TOTAL_ESCROW_FEE_OFFSET, _data);
  }

  public static Filter createWhitelistModeFilter(final int whitelistMode) {
    return Filter.createMemCompFilter(WHITELIST_MODE_OFFSET, new byte[]{(byte) whitelistMode});
  }

  public static Filter createActivationTypeFilter(final int activationType) {
    return Filter.createMemCompFilter(ACTIVATION_TYPE_OFFSET, new byte[]{(byte) activationType});
  }

  public static Filter createVaultAuthorityFilter(final PublicKey vaultAuthority) {
    return Filter.createMemCompFilter(VAULT_AUTHORITY_OFFSET, vaultAuthority);
  }

  public static Vault read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Vault read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Vault read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Vault> FACTORY = Vault::read;

  public static Vault read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pool = readPubKey(_data, i);
    i += 32;
    final var tokenVault = readPubKey(_data, i);
    i += 32;
    final var tokenOutVault = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var base = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var maxBuyingCap = getInt64LE(_data, i);
    i += 8;
    final var totalDeposit = getInt64LE(_data, i);
    i += 8;
    final var totalEscrow = getInt64LE(_data, i);
    i += 8;
    final var swappedAmount = getInt64LE(_data, i);
    i += 8;
    final var boughtToken = getInt64LE(_data, i);
    i += 8;
    final var totalRefund = getInt64LE(_data, i);
    i += 8;
    final var totalClaimedToken = getInt64LE(_data, i);
    i += 8;
    final var startVestingPoint = getInt64LE(_data, i);
    i += 8;
    final var endVestingPoint = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var poolType = _data[i] & 0xFF;
    ++i;
    final var vaultMode = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[5];
    i += Borsh.readArray(padding0, _data, i);
    final var maxDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var individualDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var depositingPoint = getInt64LE(_data, i);
    i += 8;
    final var escrowFee = getInt64LE(_data, i);
    i += 8;
    final var totalEscrowFee = getInt64LE(_data, i);
    i += 8;
    final var whitelistMode = _data[i] & 0xFF;
    ++i;
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[6];
    i += Borsh.readArray(padding1, _data, i);
    final var vaultAuthority = readPubKey(_data, i);
    i += 32;
    final var padding = new BigInteger[5];
    Borsh.readArray(padding, _data, i);
    return new Vault(_address,
                     discriminator,
                     pool,
                     tokenVault,
                     tokenOutVault,
                     quoteMint,
                     baseMint,
                     base,
                     owner,
                     maxBuyingCap,
                     totalDeposit,
                     totalEscrow,
                     swappedAmount,
                     boughtToken,
                     totalRefund,
                     totalClaimedToken,
                     startVestingPoint,
                     endVestingPoint,
                     bump,
                     poolType,
                     vaultMode,
                     padding0,
                     maxDepositingCap,
                     individualDepositingCap,
                     depositingPoint,
                     escrowFee,
                     totalEscrowFee,
                     whitelistMode,
                     activationType,
                     padding1,
                     vaultAuthority,
                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pool.write(_data, i);
    i += 32;
    tokenVault.write(_data, i);
    i += 32;
    tokenOutVault.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    base.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, maxBuyingCap);
    i += 8;
    putInt64LE(_data, i, totalDeposit);
    i += 8;
    putInt64LE(_data, i, totalEscrow);
    i += 8;
    putInt64LE(_data, i, swappedAmount);
    i += 8;
    putInt64LE(_data, i, boughtToken);
    i += 8;
    putInt64LE(_data, i, totalRefund);
    i += 8;
    putInt64LE(_data, i, totalClaimedToken);
    i += 8;
    putInt64LE(_data, i, startVestingPoint);
    i += 8;
    putInt64LE(_data, i, endVestingPoint);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) poolType;
    ++i;
    _data[i] = (byte) vaultMode;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    putInt64LE(_data, i, maxDepositingCap);
    i += 8;
    putInt64LE(_data, i, individualDepositingCap);
    i += 8;
    putInt64LE(_data, i, depositingPoint);
    i += 8;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    putInt64LE(_data, i, totalEscrowFee);
    i += 8;
    _data[i] = (byte) whitelistMode;
    ++i;
    _data[i] = (byte) activationType;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    vaultAuthority.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
