package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Multisig(PublicKey _address,
                       Discriminator discriminator,
                       int numSigners,
                       int numSigned,
                       int minSignatures,
                       int instructionAccountsLen,
                       int instructionDataLen,
                       long instructionHash,
                       PublicKey[] signers,
                       byte[] signed,
                       int bump) implements Borsh {

  public static final int BYTES = 221;
  public static final int SIGNERS_LEN = 6;
  public static final int SIGNED_LEN = 6;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NUM_SIGNERS_OFFSET = 8;
  public static final int NUM_SIGNED_OFFSET = 9;
  public static final int MIN_SIGNATURES_OFFSET = 10;
  public static final int INSTRUCTION_ACCOUNTS_LEN_OFFSET = 11;
  public static final int INSTRUCTION_DATA_LEN_OFFSET = 12;
  public static final int INSTRUCTION_HASH_OFFSET = 14;
  public static final int SIGNERS_OFFSET = 22;
  public static final int SIGNED_OFFSET = 214;
  public static final int BUMP_OFFSET = 220;

  public static Filter createNumSignersFilter(final int numSigners) {
    return Filter.createMemCompFilter(NUM_SIGNERS_OFFSET, new byte[]{(byte) numSigners});
  }

  public static Filter createNumSignedFilter(final int numSigned) {
    return Filter.createMemCompFilter(NUM_SIGNED_OFFSET, new byte[]{(byte) numSigned});
  }

  public static Filter createMinSignaturesFilter(final int minSignatures) {
    return Filter.createMemCompFilter(MIN_SIGNATURES_OFFSET, new byte[]{(byte) minSignatures});
  }

  public static Filter createInstructionAccountsLenFilter(final int instructionAccountsLen) {
    return Filter.createMemCompFilter(INSTRUCTION_ACCOUNTS_LEN_OFFSET, new byte[]{(byte) instructionAccountsLen});
  }

  public static Filter createInstructionDataLenFilter(final int instructionDataLen) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, instructionDataLen);
    return Filter.createMemCompFilter(INSTRUCTION_DATA_LEN_OFFSET, _data);
  }

  public static Filter createInstructionHashFilter(final long instructionHash) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, instructionHash);
    return Filter.createMemCompFilter(INSTRUCTION_HASH_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Multisig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Multisig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Multisig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Multisig> FACTORY = Multisig::read;

  public static Multisig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var numSigners = _data[i] & 0xFF;
    ++i;
    final var numSigned = _data[i] & 0xFF;
    ++i;
    final var minSignatures = _data[i] & 0xFF;
    ++i;
    final var instructionAccountsLen = _data[i] & 0xFF;
    ++i;
    final var instructionDataLen = getInt16LE(_data, i);
    i += 2;
    final var instructionHash = getInt64LE(_data, i);
    i += 8;
    final var signers = new PublicKey[6];
    i += Borsh.readArray(signers, _data, i);
    final var signed = new byte[6];
    i += Borsh.readArray(signed, _data, i);
    final var bump = _data[i] & 0xFF;
    return new Multisig(_address,
                        discriminator,
                        numSigners,
                        numSigned,
                        minSignatures,
                        instructionAccountsLen,
                        instructionDataLen,
                        instructionHash,
                        signers,
                        signed,
                        bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) numSigners;
    ++i;
    _data[i] = (byte) numSigned;
    ++i;
    _data[i] = (byte) minSignatures;
    ++i;
    _data[i] = (byte) instructionAccountsLen;
    ++i;
    putInt16LE(_data, i, instructionDataLen);
    i += 2;
    putInt64LE(_data, i, instructionHash);
    i += 8;
    i += Borsh.writeArray(signers, _data, i);
    i += Borsh.writeArray(signed, _data, i);
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
