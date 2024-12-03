package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Escrow(PublicKey _address,
                     Discriminator discriminator,
                     // vault address
                     PublicKey vault,
                     // owner
                     PublicKey owner,
                     // total deposited quote token
                     long totalDeposit,
                     // Total token that escrow has claimed
                     long claimedToken,
                     // Last claimed timestamp
                     long lastClaimedPoint,
                     // Whether owner has claimed for remaining quote token
                     int refunded,
                     // padding 1
                     byte[] padding1,
                     // Only has meaning in permissioned vault
                     long maxCap,
                     // padding 2
                     byte[] padding2,
                     BigInteger[] padding) implements Borsh {

  public static final int BYTES = 136;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VAULT_OFFSET = 8;
  public static final int OWNER_OFFSET = 40;
  public static final int TOTAL_DEPOSIT_OFFSET = 72;
  public static final int CLAIMED_TOKEN_OFFSET = 80;
  public static final int LAST_CLAIMED_POINT_OFFSET = 88;
  public static final int REFUNDED_OFFSET = 96;
  public static final int PADDING1_OFFSET = 97;
  public static final int MAX_CAP_OFFSET = 104;
  public static final int PADDING2_OFFSET = 112;
  public static final int PADDING_OFFSET = 120;

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createTotalDepositFilter(final long totalDeposit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalDeposit);
    return Filter.createMemCompFilter(TOTAL_DEPOSIT_OFFSET, _data);
  }

  public static Filter createClaimedTokenFilter(final long claimedToken) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, claimedToken);
    return Filter.createMemCompFilter(CLAIMED_TOKEN_OFFSET, _data);
  }

  public static Filter createLastClaimedPointFilter(final long lastClaimedPoint) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastClaimedPoint);
    return Filter.createMemCompFilter(LAST_CLAIMED_POINT_OFFSET, _data);
  }

  public static Filter createRefundedFilter(final int refunded) {
    return Filter.createMemCompFilter(REFUNDED_OFFSET, new byte[]{(byte) refunded});
  }

  public static Filter createMaxCapFilter(final long maxCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxCap);
    return Filter.createMemCompFilter(MAX_CAP_OFFSET, _data);
  }

  public static Escrow read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Escrow read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Escrow> FACTORY = Escrow::read;

  public static Escrow read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vault = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var totalDeposit = getInt64LE(_data, i);
    i += 8;
    final var claimedToken = getInt64LE(_data, i);
    i += 8;
    final var lastClaimedPoint = getInt64LE(_data, i);
    i += 8;
    final var refunded = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[7];
    i += Borsh.readArray(padding1, _data, i);
    final var maxCap = getInt64LE(_data, i);
    i += 8;
    final var padding2 = new byte[8];
    i += Borsh.readArray(padding2, _data, i);
    final var padding = new BigInteger[1];
    Borsh.readArray(padding, _data, i);
    return new Escrow(_address,
                      discriminator,
                      vault,
                      owner,
                      totalDeposit,
                      claimedToken,
                      lastClaimedPoint,
                      refunded,
                      padding1,
                      maxCap,
                      padding2,
                      padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vault.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, totalDeposit);
    i += 8;
    putInt64LE(_data, i, claimedToken);
    i += 8;
    putInt64LE(_data, i, lastClaimedPoint);
    i += 8;
    _data[i] = (byte) refunded;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    putInt64LE(_data, i, maxCap);
    i += 8;
    i += Borsh.writeArray(padding2, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
