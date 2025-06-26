package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Config(PublicKey _address,
                     Discriminator discriminator,
                     // Account with authority over this PDA.
                     PublicKey authority,
                     // We want to expire funds after some time so that validators can be refunded the rent.
                     // Expired funds will get transferred to this account.
                     PublicKey expiredFundsAccount,
                     // Specifies the number of epochs a merkle root is valid for before expiring.
                     long numEpochsValid,
                     // The maximum commission a validator can set on their distribution account.
                     int maxValidatorCommissionBps,
                     // The bump used to generate this account
                     int bump) implements Borsh {

  public static final int BYTES = 83;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(155, 12, 170, 224, 30, 250, 204, 130);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int AUTHORITY_OFFSET = 8;
  public static final int EXPIRED_FUNDS_ACCOUNT_OFFSET = 40;
  public static final int NUM_EPOCHS_VALID_OFFSET = 72;
  public static final int MAX_VALIDATOR_COMMISSION_BPS_OFFSET = 80;
  public static final int BUMP_OFFSET = 82;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createExpiredFundsAccountFilter(final PublicKey expiredFundsAccount) {
    return Filter.createMemCompFilter(EXPIRED_FUNDS_ACCOUNT_OFFSET, expiredFundsAccount);
  }

  public static Filter createNumEpochsValidFilter(final long numEpochsValid) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, numEpochsValid);
    return Filter.createMemCompFilter(NUM_EPOCHS_VALID_OFFSET, _data);
  }

  public static Filter createMaxValidatorCommissionBpsFilter(final int maxValidatorCommissionBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, maxValidatorCommissionBps);
    return Filter.createMemCompFilter(MAX_VALIDATOR_COMMISSION_BPS_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Config read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Config read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Config read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Config> FACTORY = Config::read;

  public static Config read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var expiredFundsAccount = readPubKey(_data, i);
    i += 32;
    final var numEpochsValid = getInt64LE(_data, i);
    i += 8;
    final var maxValidatorCommissionBps = getInt16LE(_data, i);
    i += 2;
    final var bump = _data[i] & 0xFF;
    return new Config(_address,
                      discriminator,
                      authority,
                      expiredFundsAccount,
                      numEpochsValid,
                      maxValidatorCommissionBps,
                      bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    expiredFundsAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, numEpochsValid);
    i += 8;
    putInt16LE(_data, i, maxValidatorCommissionBps);
    i += 2;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
