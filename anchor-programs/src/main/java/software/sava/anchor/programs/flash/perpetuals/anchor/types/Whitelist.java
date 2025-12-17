package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Whitelist(PublicKey _address,
                        Discriminator discriminator,
                        PublicKey owner,
                        boolean isInitialized,
                        int bump,
                        boolean isSwapFeeExempt,
                        boolean isDepositFeeExempt,
                        boolean isWithdrawalFeeExempt,
                        byte[] buffer,
                        PublicKey pool) implements Borsh {

  public static final int BYTES = 80;
  public static final int BUFFER_LEN = 3;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OWNER_OFFSET = 8;
  public static final int IS_INITIALIZED_OFFSET = 40;
  public static final int BUMP_OFFSET = 41;
  public static final int IS_SWAP_FEE_EXEMPT_OFFSET = 42;
  public static final int IS_DEPOSIT_FEE_EXEMPT_OFFSET = 43;
  public static final int IS_WITHDRAWAL_FEE_EXEMPT_OFFSET = 44;
  public static final int BUFFER_OFFSET = 45;
  public static final int POOL_OFFSET = 48;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createIsInitializedFilter(final boolean isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) (isInitialized ? 1 : 0)});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createIsSwapFeeExemptFilter(final boolean isSwapFeeExempt) {
    return Filter.createMemCompFilter(IS_SWAP_FEE_EXEMPT_OFFSET, new byte[]{(byte) (isSwapFeeExempt ? 1 : 0)});
  }

  public static Filter createIsDepositFeeExemptFilter(final boolean isDepositFeeExempt) {
    return Filter.createMemCompFilter(IS_DEPOSIT_FEE_EXEMPT_OFFSET, new byte[]{(byte) (isDepositFeeExempt ? 1 : 0)});
  }

  public static Filter createIsWithdrawalFeeExemptFilter(final boolean isWithdrawalFeeExempt) {
    return Filter.createMemCompFilter(IS_WITHDRAWAL_FEE_EXEMPT_OFFSET, new byte[]{(byte) (isWithdrawalFeeExempt ? 1 : 0)});
  }

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Whitelist read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Whitelist read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Whitelist read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Whitelist> FACTORY = Whitelist::read;

  public static Whitelist read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var isInitialized = _data[i] == 1;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var isSwapFeeExempt = _data[i] == 1;
    ++i;
    final var isDepositFeeExempt = _data[i] == 1;
    ++i;
    final var isWithdrawalFeeExempt = _data[i] == 1;
    ++i;
    final var buffer = new byte[3];
    i += Borsh.readArray(buffer, _data, i);
    final var pool = readPubKey(_data, i);
    return new Whitelist(_address,
                         discriminator,
                         owner,
                         isInitialized,
                         bump,
                         isSwapFeeExempt,
                         isDepositFeeExempt,
                         isWithdrawalFeeExempt,
                         buffer,
                         pool);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    _data[i] = (byte) (isInitialized ? 1 : 0);
    ++i;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) (isSwapFeeExempt ? 1 : 0);
    ++i;
    _data[i] = (byte) (isDepositFeeExempt ? 1 : 0);
    ++i;
    _data[i] = (byte) (isWithdrawalFeeExempt ? 1 : 0);
    ++i;
    i += Borsh.writeArrayChecked(buffer, 3, _data, i);
    pool.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
